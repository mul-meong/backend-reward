package com.mulmeong.reward.point.domain.entity;

import lombok.Getter;

@Getter
public enum HistoryReason {
    FEED_CREATE,
    SHORTS_CREATE,
    COMMENT_CREATE,
    CONTEST_WIN,
    CONTEST_JOIN
}
