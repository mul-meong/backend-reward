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
public class MemberBadgeCreateEvent {

    private String memberUuid;
    private Long badgeId;
    private String badgeName;
    private String badgeImageUrl;
    private String badgeDescription;
    private boolean equipped;

    public static MemberBadgeCreateEvent from(MemberBadge memberBadge, boolean equipped) {
        return MemberBadgeCreateEvent.builder()
                .memberUuid(memberBadge.getMemberUuid())
                .badgeId(memberBadge.getBadge().getId())
                .badgeName(memberBadge.getBadge().getName())
                .badgeImageUrl(memberBadge.getBadge().getImageUrl())
                .badgeDescription(memberBadge.getBadge().getDescription())
                .equipped(equipped)
                .build();
    }
}
