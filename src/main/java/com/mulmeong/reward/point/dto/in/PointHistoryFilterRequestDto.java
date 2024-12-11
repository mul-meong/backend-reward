package com.mulmeong.reward.point.dto.in;

import com.mulmeong.reward.point.domain.entity.HistoryReason;
import com.mulmeong.reward.point.domain.entity.PointChangeType;
import com.mulmeong.reward.point.domain.model.SortType;
import com.mulmeong.reward.point.dto.model.BasePaginationDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PointHistoryFilterRequestDto extends BasePaginationDto {

    private String memberUuid;
    private HistoryReason reason;
    private PointChangeType pointChangeType;

    public static PointHistoryFilterRequestDto toDto(String memberUuid, String reason, String pointChangeType,
                                                     String sortBy, Long nextCursor, Integer pageSize, Integer pageNo) {

        return PointHistoryFilterRequestDto.builder()
                .memberUuid(memberUuid)
                .reason(HistoryReason.safeConvertFrom(reason))
                .pointChangeType(PointChangeType.safeConvertFrom(pointChangeType))
                .sortType(SortType.valueOf(sortBy.toUpperCase()))
                .lastId(nextCursor)
                .pageSize(pageSize)
                .pageNo(pageNo)
                .build();
    }

    @Builder
    public PointHistoryFilterRequestDto(String memberUuid, HistoryReason reason, PointChangeType pointChangeType,
                                        SortType sortType, Long lastId, Integer pageSize, Integer pageNo) {

        super(sortType, lastId, pageSize, pageNo);
        this.memberUuid = memberUuid;
        this.reason = reason;
        this.pointChangeType = pointChangeType;
    }
}
