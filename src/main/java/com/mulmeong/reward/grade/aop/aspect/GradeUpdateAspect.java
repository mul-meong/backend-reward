package com.mulmeong.reward.grade.aop.aspect;

import com.mulmeong.event.member.MemberGradeUpdateEvent;
import com.mulmeong.reward.common.exception.BaseException;
import com.mulmeong.reward.grade.domain.model.GradeThreshold;
import com.mulmeong.reward.grade.infrastructure.GradeEventPublisher;
import com.mulmeong.reward.point.domain.document.MemberPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.mulmeong.reward.common.response.BaseResponseStatus.NO_POINT;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class GradeUpdateAspect {

    private final GradeEventPublisher gradeEventPublisher;

    /**
     * GradeUpdate 어노테이션이 붙은 메서드 실행 후 등급 업데이트 이벤트를 발행합니다.
     * 현재 등급과 업데이트된 등급이 다른 경우에만 발행 => ReadDB에 등급 업데이트, 알림 전송 ...
     * 이렇게 처리할 시 point와 grade의 결합도가 높아지고 포인트 수정마다 연산이 들어가므로 Spring Batch를 이용한 일괄처리를 생각했으나,
     * 프로젝트 남은 시간을 고려해 일단 변경시마다 개별 처리로 구현하였습니다. // todo : Spring Batch 적용
     *
     * @param joinPoint   조인 포인트 : 메서드 실행 시점의 정보를 담고 있는 객체
     * @param returnValue 메서드 반환값 : 포인트 변경 후의 MemberPoint 객체
     *
     */
    @Deprecated // AOP를 사용하지 않고, 서비스 계층에서 직접 처리하는 방식으로 변경*(내부 메서드는 AOP로 처리가 어렵고,이를위한 코드가 복잡해짐)
    @AfterReturning(
            pointcut = "@annotation(com.mulmeong.reward.grade.aop.annotation.GradeUpdate)",
            returning = "returnValue")
    public void handleGradeUpdate(JoinPoint joinPoint, Object returnValue) {

        // 메서드 결과가 null 또는, MemberPoint가 아닌 경우 예외 발생

        log.info("returnValue : {}", returnValue);


        if (!(returnValue instanceof MemberPoint memberPoint)) {
            throw new BaseException(NO_POINT);
        }

        Object[] args = joinPoint.getArgs();
        log.info(Arrays.toString(args));
        String memberUuid = (String) args[0];
        log.info(memberUuid);
        int pointChange = (int) args[1];
        log.info(String.valueOf(pointChange));

        // 등급 계산 및 이벤트 발행
        int currentPoint = memberPoint.getPoint();
        int updatedPoint = currentPoint + pointChange;

        Long currentGradeId = GradeThreshold.getGradeIdByPoint(currentPoint);
        Long updatedGradeId = GradeThreshold.getGradeIdByPoint(updatedPoint);
        log.info("currentGradeId : {}", currentGradeId);
        log.info("updatedGradeId : {}", updatedGradeId);

        if (!currentGradeId.equals(updatedGradeId)) {
            gradeEventPublisher.send(MemberGradeUpdateEvent.of(memberUuid, updatedGradeId));
        }
    }
}