package com.mulmeong.reward.badge.dto.in;

import com.mulmeong.reward.badge.domain.MemberBadge;
import com.mulmeong.reward.badge.vo.in.MemberBadgeUpdateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberBadgeUpdateDto {

    private String memberUuid;
    private Long badgeId;
    private Boolean equipped;

    public static MemberBadgeUpdateDto toDto(MemberBadgeUpdateVo vo, String memberUuid, Long badgeId) {
        return MemberBadgeUpdateDto.builder()
                .memberUuid(memberUuid)
                .badgeId(badgeId)
                .equipped(vo.getEquipped())
                .build();
    }

    public MemberBadge toEntity(MemberBadge memberBadge) {
        return MemberBadge.builder()
                .id(memberBadge.getId())
                .memberUuid(memberBadge.getMemberUuid())
                .badgeId(memberBadge.getBadgeId())
                .equipped(equipped)
                .build();
    }
}
