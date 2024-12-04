package com.mulmeong.reward.point.dto.out;

import com.mulmeong.reward.point.domain.entity.HistoryReason;
import com.mulmeong.reward.point.domain.entity.HistoryType;
import com.mulmeong.reward.point.domain.entity.PointHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PointHistoryDto {

    private Long id;
    private String memberUuid;
    private HistoryReason reason;
    private HistoryType historyType;
    private Integer point;
    private LocalDateTime createdAt;

    public static PointHistoryDto fromEntity(PointHistory pointHistory) {
        return PointHistoryDto.builder()
                .id(pointHistory.getId())
                .memberUuid(pointHistory.getMemberUuid())
                .reason(pointHistory.getReason())
                .historyType(pointHistory.getHistoryType())
                .point(pointHistory.getPoint())
                .createdAt(pointHistory.getCreatedAt())
                .build();
    }
}
