package com.mulmeong.reward.grade.dto.in;

import com.mulmeong.reward.grade.domain.Grade;
import com.mulmeong.reward.grade.vo.in.GradeCreateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GradeCreateDto {

    private String name;
    private Integer pointThreshold;

    public static GradeCreateDto toDto(GradeCreateVo vo) {
        return GradeCreateDto.builder()
                .name(vo.getName())
                .pointThreshold(vo.getPointThreshold())
                .build();
    }

    public Grade toEntity() {
        return Grade.builder()
                .name(name)
                .pointThreshold(pointThreshold)
                .build();
    }
}
