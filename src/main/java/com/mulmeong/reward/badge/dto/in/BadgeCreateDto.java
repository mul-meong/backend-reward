package com.mulmeong.reward.badge.dto.in;

import com.mulmeong.reward.badge.domain.Badge;
import com.mulmeong.reward.badge.vo.in.BadgeCreateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BadgeCreateDto {

    private String name;
    private String imageUrl;
    private String description;

    public static BadgeCreateDto toDto(BadgeCreateVo vo) {
        return BadgeCreateDto.builder()
                .name(vo.getName())
                .imageUrl(vo.getImageUrl())
                .description(vo.getDescription())
                .build();
    }

    public Badge toEntity() {
        return Badge.builder()
                .name(name)
                .imageUrl(imageUrl)
                .description(description)
                .build();
    }
}
