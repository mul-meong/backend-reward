package com.mulmeong.reward.point.aop.aspect;


import com.mulmeong.reward.point.domain.entity.EventType;
import com.mulmeong.reward.point.domain.entity.MemberPointHistory;
import com.mulmeong.reward.point.infrastructure.MemberPointHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MemberPointHistoryAspect {

    private final MemberPointHistoryRepository historyRepository;

    @AfterReturning(pointcut = "@annotation(com.mulmeong.reward.point.aop.annotation.LogPointHistory)")
    @Transactional
    public void logPointHistory(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String memberUuid = (String) args[0];
        EventType eventType = (EventType) args[1];

        log.info("히스토리 기록: memberUuid={}, reason={}, historyType={}, point={}",
                memberUuid, eventType.getReason(), eventType.getHistoryType(), eventType.getPoint());

        // 히스토리 저장
        MemberPointHistory history = MemberPointHistory.builder()
                .memberUuid(memberUuid)
                .reason(eventType.getReason())
                .historyType(eventType.getHistoryType())
                .point(eventType.getPoint())
                .build();

        historyRepository.save(history);
        log.info("포인트 히스토리 저장 완료: {}", history);
    }
}
