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
     * 포인트 증가 이벤트 처리
     * 1. Redis 에서 금일 횟수 조회
     * 2. 각 이벤트의 최대 횟수 초과 여부 확인 => 초과시 return;
     * 3. (미초과시) 횟수 증가 및 TTL 설정해 Redis 에 횟수 저장.
     * 4. 포인트 업데이트 및 어노테이션을 통한 히스토리 저장
     *
     * @param memberUuid 회원 uuid
     */
    @Override
    @LogPointHistory
    public void addPointByEvent(String memberUuid, EventType eventType) {
        String todayKey = eventType.getKeyPrefix() + ":" + memberUuid + ":" + LocalDate.now();

        if (getCount(todayKey) >= eventType.getMaxDailyCount()) {
            return;
        }

        updateMemberPoint(memberUuid, eventType.getPoint());
        addTodayCount(todayKey);
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
