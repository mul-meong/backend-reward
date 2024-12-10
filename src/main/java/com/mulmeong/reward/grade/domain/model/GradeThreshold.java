package com.mulmeong.reward.grade.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 등급별 포인트 임계값과 그에따른 gradeId를 정의한 Enum 클래스
 * 포인트 변화시 등급 자동 변경 구현에 사용, Spring Batch 일괄처리 전 임시 구현용.
 * ** 임계값, id 모두 DB와의 연동 필요 **.
 */
@Getter
@RequiredArgsConstructor
public enum GradeThreshold {

    PLANKTON(0, 1L),
    EGG(50, 2L),
    FISH(200, 3L),
    ADULT_FISH(500, 4L),
    WHALE(1000, 5L),
    DRAGON(3000, 6L);

    private final int pointThreshold;
    private final Long id;

    public static Long getGradeIdByPoint(int point) {
        Long result = PLANKTON.getId(); // 기본 등급은 플랑크톤

        // 현재 포인트를 만족하는 가장 높은 등급으로 갱신
        for (GradeThreshold grade : GradeThreshold.values()) {
            if (point >= grade.getPointThreshold()) {
                result = grade.getId();
            } else {
                break;
            }
        }
        return result;
    }
}
