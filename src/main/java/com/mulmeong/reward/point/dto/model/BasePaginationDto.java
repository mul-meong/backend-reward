package com.mulmeong.reward.point.dto.model;

import com.mulmeong.reward.point.domain.model.SortType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BasePaginationDto {
    private SortType sortType;
    private Long lastId;
    private Integer pageSize;
    private Integer pageNo;
}
