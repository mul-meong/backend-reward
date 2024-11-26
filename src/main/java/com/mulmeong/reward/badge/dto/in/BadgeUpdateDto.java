package com.mulmeong.reward.badge.dto.in;

import com.mulmeong.reward.badge.domain.Badge;
import com.mulmeong.reward.badge.vo.in.BadgeUpdateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BadgeUpdateDto {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;

    public static BadgeUpdateDto toDto(BadgeUpdateVo vo, Long id) {
        return BadgeUpdateDto.builder()
                .id(id)
                .name(vo.getName())
                .imageUrl(vo.getImageUrl())
                .description(vo.getDescription())
                .build();
    }

    public Badge toEntity(Badge badge) {
        return Badge.builder()
                .id(badge.getId())
                .name(name)
                .imageUrl(imageUrl)
                .description(description)
                .build();
    }
}
