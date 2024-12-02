package com.mulmeong.reward.badge.application;

import com.mulmeong.reward.badge.dto.in.MemberBadgeCreateDto;
import com.mulmeong.reward.badge.dto.in.MemberBadgeUpdateDto;
import com.mulmeong.reward.badge.dto.out.MemberBadgeDto;

import java.util.List;

public interface MemberBadgeService {

    void createMemberBadge(MemberBadgeCreateDto memberBadgeCreateDto);

    void updateBadgeEquipStatus(MemberBadgeUpdateDto memberBadgeUpdateDto);

    List<MemberBadgeDto> getMemberBadges(String memberUuid);

    MemberBadgeDto getMemberBadge(String memberUuid, Long badgeId);
}
