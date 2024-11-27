package com.mulmeong.reward.badge.dto.out;

import com.mulmeong.reward.badge.domain.Badge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BadgeDto {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;

    public static BadgeDto fromEntity(Badge badge) {
        return BadgeDto.builder()
                .id(badge.getId())
                .name(badge.getName())
                .imageUrl(badge.getImageUrl())
                .description(badge.getDescription())
                .build();
    }
}