package com.mulmeong.reward.grade.dto.out;

import com.mulmeong.reward.grade.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GradeDto {

    private Long id;
    private String name;
    private Integer pointThreshold;

    public static GradeDto fromEntity(Grade grade) {
        return GradeDto.builder()
                .id(grade.getId())
                .name(grade.getName())
                .pointThreshold(grade.getPointThreshold())
                .build();
    }
}
