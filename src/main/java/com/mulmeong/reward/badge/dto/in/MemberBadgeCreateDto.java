package com.mulmeong.reward.badge.dto.in;

import com.mulmeong.reward.badge.domain.Badge;
import com.mulmeong.reward.badge.domain.MemberBadge;
import com.mulmeong.reward.badge.vo.in.BadgeCreateVo;
import com.mulmeong.reward.badge.vo.in.MemberBadgeCreateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberBadgeCreateDto {

    private String memberUuid;
    private Long badgeId;

    public static MemberBadgeCreateDto toDto(MemberBadgeCreateVo vo, String memberUuid) {
        return MemberBadgeCreateDto.builder()
                .memberUuid(memberUuid)
                .badgeId(vo.getBadgeId())
                .build();
    }

    public MemberBadge toEntity() {
        return MemberBadge.builder()
                .memberUuid(memberUuid)
                .badge(Badge.builder().id(badgeId).build())
                .equipped(false)
                .build();
    }
}
