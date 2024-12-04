package com.mulmeong.reward.point.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.mulmeong.reward.point.domain.entity.HistoryReason.*;
import static com.mulmeong.reward.point.domain.entity.PointChangeType.*;

@Getter
@RequiredArgsConstructor
public enum EventType {

    // + point
    FEED_CREATED("feed_count", 1, 5, FEED_CREATE, INCREASE),
    SHORTS_CREATED("shorts_count", 1, 5, SHORTS_CREATE, INCREASE),
    CONTEST_JOINED("contest_join_count", 1, 5, CONTEST_JOIN, INCREASE),
    COMMENT_CREATED("comment_count", 10, 1, COMMENT_CREATE, INCREASE),
    CONTEST_WON("contest_win_count", 1, 30, CONTEST_WIN, INCREASE),

    // - point
    FEED_DELETED("", 0, -5, FEED_DELETE, DECREASE),
    SHORTS_DELETED("", 0, -5, SHORTS_DELETE, DECREASE),
    COMMENT_DELETED("", 0, -1, COMMENT_DELETE, DECREASE),
    REPORT_APPROVED("", 0, -30, REPORTED_APPROVE, DECREASE);


    private final String keyPrefix;
    private final int maxDailyCount;
    private final int point;
    private final HistoryReason reason;
    private final PointChangeType pointChangeType;
}
