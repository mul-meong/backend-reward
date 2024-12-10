package com.mulmeong.reward.point.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {

    LATEST("최신순"),
    OLDEST("오래된순");

    private final String sortType;
}
