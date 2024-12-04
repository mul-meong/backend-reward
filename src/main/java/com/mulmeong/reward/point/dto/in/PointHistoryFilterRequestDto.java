package com.mulmeong.reward.point.dto.in;

import com.mulmeong.reward.point.domain.entity.HistoryReason;
import com.mulmeong.reward.point.domain.entity.HistoryType;
import com.mulmeong.reward.point.domain.model.SortType;
import com.mulmeong.reward.point.dto.model.BasePaginationDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PointHistoryFilterRequestDto extends BasePaginationDto {

    private String memberUuid;
    private HistoryReason reason;
    private HistoryType historyType;

    public static PointHistoryFilterRequestDto toDto(String memberUuid, String reason, String historyType,
                                                     String sortBy, Long lastId, Integer pageSize, Integer pageNo) {

        return PointHistoryFilterRequestDto.builder()
                .memberUuid(memberUuid)
                .reason(HistoryReason.safeConvertFrom(reason))
                .historyType(HistoryType.safeConvertFrom(historyType))
                .sortType(SortType.valueOf(sortBy.toUpperCase()))
                .lastId(lastId)
                .pageSize(pageSize)
                .pageNo(pageNo)
                .build();
    }

    @Builder
    public PointHistoryFilterRequestDto(String memberUuid, HistoryReason reason, HistoryType historyType,
                                        SortType sortType, Long lastId, Integer pageSize, Integer pageNo) {

        super(sortType, lastId, pageSize, pageNo);
        this.memberUuid = memberUuid;
        this.reason = reason;
        this.historyType = historyType;
    }
}
