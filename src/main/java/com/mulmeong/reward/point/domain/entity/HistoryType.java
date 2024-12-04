package com.mulmeong.reward.point.domain.entity;

import com.mulmeong.reward.common.exception.BaseException;
import lombok.Getter;

import static com.mulmeong.reward.common.response.BaseResponseStatus.INVALID_HISTORY_TYPE;

@Getter
public enum HistoryType {
    INCREASE,
    DECREASE;

    public static HistoryType safeConvertFrom(String historyType) {
        try {
            return historyType != null ? HistoryType.valueOf(historyType.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            throw new BaseException(INVALID_HISTORY_TYPE);
        }
    }

}
