package com.mulmeong.reward.point.domain.entity;

import com.mulmeong.reward.common.exception.BaseException;
import lombok.Getter;

import static com.mulmeong.reward.common.response.BaseResponseStatus.INVALID_HISTORY_REASON;

@Getter
public enum HistoryReason {
    FEED_CREATE,
    SHORTS_CREATE,
    COMMENT_CREATE,
    CONTEST_WIN,
    CONTEST_JOIN,

    // Delete
    FEED_DELETE,
    SHORTS_DELETE,
    COMMENT_DELETE,
    REPORTED_APPROVE;


    public static HistoryReason safeConvertFrom(String reason) {
        try {
            return reason != null ? HistoryReason.valueOf(reason.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            throw new BaseException(INVALID_HISTORY_REASON);
        }
    }
}
