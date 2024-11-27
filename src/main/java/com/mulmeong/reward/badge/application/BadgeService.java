package com.mulmeong.reward.badge.application;

import com.mulmeong.reward.badge.dto.in.BadgeCreateDto;
import com.mulmeong.reward.badge.dto.in.BadgeUpdateDto;
import com.mulmeong.reward.badge.dto.out.BadgeDto;

import java.util.List;

public interface BadgeService {

    void createBadge(BadgeCreateDto badgeCreateDto);

    void updateBadge(BadgeUpdateDto badgeUpdateDto);

    void deleteBadge(Long badgeId);

    BadgeDto getBadge(Long badgeId);

    List<BadgeDto> getAllBadges();

}
