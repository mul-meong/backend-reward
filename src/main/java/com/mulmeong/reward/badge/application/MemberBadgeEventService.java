package com.mulmeong.reward.badge.application;

import com.mulmeong.reward.badge.dto.in.MemberBadgeCreateDto;

public interface MemberBadgeEventService {

    public void createMemberBadge(String memberUuid, Long badgeId);
}
