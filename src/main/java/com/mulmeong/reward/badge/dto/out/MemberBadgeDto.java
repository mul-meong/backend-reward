package com.mulmeong.reward.badge.dto.out;

import com.mulmeong.reward.badge.domain.MemberBadge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberBadgeDto {

    private Long badgeId;
    private Boolean equipped;

    public static MemberBadgeDto fromEntity(MemberBadge memberBadge) {
        return MemberBadgeDto.builder()
                .badgeId(memberBadge.getBadgeId())
                .equipped(memberBadge.getEquipped())
                .build();
    }
}
