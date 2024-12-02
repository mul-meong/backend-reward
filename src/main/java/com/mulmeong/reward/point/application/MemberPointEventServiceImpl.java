package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.common.exception.BaseException;
import com.mulmeong.reward.point.domain.document.MemberPoint;
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

    private static final String COMMENT_COUNT_KEY_PREFIX = "comment_count:";
    private static final int MAX_DAILY_COMMENT_COUNTS = 10;
    private static final int COMMENT_POINT = 1;

    @Override
    public void createMemberPointDocument(MemberCreateEvent event) {
        memberPointRepository.save(event.toEntity());
    }

    /**
     * 피드 댓글 작성시 포인트 증가
     * 1. Redis에서 댓글 작성 횟수 조회
     * 2. 댓글 작성 횟수가 10회 이상이면 포인트 증가하지 않음
     * 3. 댓글 작성 횟수 증가 및 TTL 설정
     * 4. 포인트 업데이트, 없는 경우 예외 처리
     *
     * @param memberUuid 회원 uuid
     */
    @Override
    public void addPointByComment(String memberUuid) {
        String todayKey = COMMENT_COUNT_KEY_PREFIX + memberUuid + ":" + LocalDate.now();
        log.info("TodayKey: {}", todayKey);

        if (isDailyLimitExceeded(getCount(todayKey))) {
            return;
        }
        updateMemberPoint(memberUuid, COMMENT_POINT);
        addTodayCount(todayKey);
    }

    // 금일 횟수 조회
    private Integer getCount(String todayKey) {
        Integer count = (Integer) redisTemplate.opsForValue().get(todayKey);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    // 일정 횟수 초과시 true 반환 => 포인트 증가하지 않음
    private static boolean isDailyLimitExceeded(Integer count) {
        log.info("today count: {}", count);

        return count >= MAX_DAILY_COMMENT_COUNTS;
    }

    // Redis 키 증가 및 TTL 설정
    private void addTodayCount(String todayKey) {
        redisTemplate.opsForValue().increment(todayKey); // 댓글 작성 횟수 증가
        redisTemplate.expire(todayKey, 1, TimeUnit.DAYS); // TTL 설정(당일)
    }

    // 포인트 업데이트, 없는 경우 예외 처리
    private void updateMemberPoint(String memberUuid, int point) {
        // 포인트 조회
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
