package com.mulmeong.reward.grade.dto.in;

import com.mulmeong.reward.grade.domain.entity.Grade;
import com.mulmeong.reward.grade.vo.in.GradeUpdateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GradeUpdateDto {

    private Long id;
    private String name;
    private Integer pointThreshold;
    private String imageUrl;

    public static GradeUpdateDto toDto(GradeUpdateVo vo, Long gradeId) {
        return GradeUpdateDto.builder()
                .id(gradeId)
                .name(vo.getName())
                .pointThreshold(vo.getPointThreshold())
                .imageUrl(vo.getImageUrl())
                .build();
    }

    public Grade toEntity(Grade grade) {
        return Grade.builder()
                .id(grade.getId())
                .name(name)
                .pointThreshold(pointThreshold)
                .imageUrl(imageUrl)
                .build();
    }
}
