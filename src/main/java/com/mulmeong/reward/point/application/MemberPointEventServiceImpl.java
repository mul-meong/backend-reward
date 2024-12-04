package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.common.exception.BaseException;
import com.mulmeong.reward.point.aop.annotation.LogPointHistory;
import com.mulmeong.reward.point.domain.document.MemberPoint;
import com.mulmeong.reward.point.domain.entity.EventType;
import com.mulmeong.reward.point.infrastructure.MemberPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static com.mulmeong.reward.common.response.BaseResponseStatus.NO_POINT;
import static com.mulmeong.reward.point.domain.entity.HistoryType.INCREASE;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointEventServiceImpl implements MemberPointEventService {

    private final MemberPointRepository memberPointRepository;

    private final RedisTemplate<String, Object> redisTemplate;
    private final MongoTemplate mongoTemplate;

    /**
     * 회원 생성시 Mongo-db에 MemberPoint 문서 생성.
     *
     * @param event 회원 생성 Event
     */
    @Override
    public void createMemberPointDocument(MemberCreateEvent event) {
        memberPointRepository.save(event.toEntity());
    }


    /**
     * 이벤트가 감소/증가냐에 따른 포인트 이벤트 분기.
     * 성공 반환시 히스토리 저장(AOP).
     *
     * @param memberUuid 포인트 조정 대상인 회원 uuid
     * @param eventType  redis key prefix, 포인트, 최대 횟수, 사유 등을 미리 정의한 enum
     * @return 성공 여부 => T: 히스토리 저장, F: 히스토리 저장 X
     */
    @Override
    @LogPointHistory
    public boolean updatePointByEvent(String memberUuid, EventType eventType) {

        return switch (eventType.getHistoryType()) {
            case INCREASE -> handleIncreaseEvent(memberUuid, eventType);
            case DECREASE -> handleDecreaseEvent(memberUuid, eventType.getPoint());
        };
    }

    /**
     * 포인트 증가 이벤트 처리
     * 1. Redis 에서 금일 횟수 조회
     * 2. 각 이벤트의 최대 횟수 초과 여부 확인 => 초과시 return;
     * 3. (미초과시) 횟수 증가 및 TTL 설정해 Redis 에 횟수 저장
     * 4. 포인트 업데이트.
     */
    private boolean handleIncreaseEvent(String memberUuid, EventType eventType) {
        String todayKey = eventType.getKeyPrefix() + ":" + memberUuid + ":" + LocalDate.now();

        if (getCount(todayKey) >= eventType.getMaxDailyCount()) {
            return false;
        }

        updateMemberPoint(memberUuid, eventType.getPoint());
        addTodayCount(todayKey);
        return true;
    }

    /**
     * 포인트 감소 이벤트 처리 : 증가와 달리 일일 한도가 없음.
     * 포인트 업데이트만 처리.
     */
    public boolean handleDecreaseEvent(String memberUuid, int updatePoint) {
        updateMemberPoint(memberUuid, updatePoint);
        return true;
    }

    // 금일 횟수 조회 메서드, 없는 경우 0 반환
    private Integer getCount(String todayKey) {
        Integer count = (Integer) redisTemplate.opsForValue().get(todayKey);
        return count == null ? 0 : count;
    }

    // Redis 키 증가 및 TTL 설정 메서드
    private void addTodayCount(String todayKey) {
        redisTemplate.opsForValue().increment(todayKey); // 횟수 증가
        redisTemplate.expire(todayKey, 1, TimeUnit.DAYS); // TTL 설정(당일)
    }

    // 포인트 업데이트, 없는 경우 예외 처리
    private void updateMemberPoint(String memberUuid, int point) {
        // 포인트 조회와 동시에 수정
        Update update = new Update().inc("point", point);
        MemberPoint memberPoint = mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(memberUuid)),
                update,
                MemberPoint.class
        );

        if (memberPoint == null) {
            throw new BaseException(NO_POINT);
        }
    }
}
