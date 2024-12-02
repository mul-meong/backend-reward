package com.mulmeong.event.member;

import com.mulmeong.reward.badge.domain.MemberBadge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberBadgeUpdateEvent {
    private String memberUuid;
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private boolean equipped;

    public static MemberBadgeUpdateEvent from(MemberBadge memberBadge, boolean equipped) {
        return MemberBadgeUpdateEvent.builder()
                .memberUuid(memberBadge.getMemberUuid())
                .id(memberBadge.getBadge().getId())
                .name(memberBadge.getBadge().getName())
                .imageUrl(memberBadge.getBadge().getImageUrl())
                .description(memberBadge.getBadge().getDescription())
                .equipped(equipped)
                .build();
    }
}
