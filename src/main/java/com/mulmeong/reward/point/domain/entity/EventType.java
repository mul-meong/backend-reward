package com.mulmeong.reward.point.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.mulmeong.reward.point.domain.entity.HistoryReason.*;
import static com.mulmeong.reward.point.domain.entity.HistoryType.*;

@Getter
@RequiredArgsConstructor
public enum EventType {

    FEED_CREATED("feed_count", 1, 5, FEED_CREATE, INCREASE),
    SHORTS_CREATED("shorts_count", 1, 5, SHORTS_CREATE, INCREASE),
    CONTEST_JOINED("contest_join_count", 1, 5, CONTEST_JOIN, INCREASE),
    COMMENT_CREATED("comment_count", 10, 1, COMMENT_CREATE, INCREASE),
    CONTEST_WON("contest_win_count", 1, 30, CONTEST_WIN, INCREASE);

    private final String keyPrefix;
    private final int maxDailyCount;
    private final int point;
    private final HistoryReason reason;
    private final HistoryType historyType;
}
