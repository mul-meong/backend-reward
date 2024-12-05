package com.mulmeong.reward.grade.dto.in;

import com.mulmeong.reward.grade.domain.entity.Grade;
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
    private String imageUrl;

    public static GradeCreateDto toDto(GradeCreateVo vo) {
        return GradeCreateDto.builder()
                .name(vo.getName())
                .pointThreshold(vo.getPointThreshold())
                .imageUrl(vo.getImageUrl())
                .build();
    }

    public Grade toEntity() {
        return Grade.builder()
                .name(name)
                .pointThreshold(pointThreshold)
                .imageUrl(imageUrl)
                .build();
    }
}
