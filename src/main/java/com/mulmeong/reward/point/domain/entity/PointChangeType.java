package com.mulmeong.reward.point.domain.entity;

import com.mulmeong.reward.common.exception.BaseException;
import lombok.Getter;

import static com.mulmeong.reward.common.response.BaseResponseStatus.INVALID_HISTORY_TYPE;

@Getter
public enum PointChangeType {
    INCREASE,
    DECREASE;

    public static PointChangeType safeConvertFrom(String pointChangeType) {
        try {
            return pointChangeType != null ? PointChangeType.valueOf(pointChangeType.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            throw new BaseException(INVALID_HISTORY_TYPE);
        }
    }

}
