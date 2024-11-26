package com.mulmeong.reward.badge.application;

import com.mulmeong.reward.badge.dto.BadgeDto;
import com.mulmeong.reward.badge.dto.in.BadgeCreateDto;
import com.mulmeong.reward.badge.dto.in.BadgeUpdateDto;

import java.util.List;

public interface BadgeService {

    void createBadge(BadgeCreateDto badgeCreateDto);

    BadgeDto getBadge(Long badgeId);

    List<BadgeDto> getAllBadges();

    void updateBadge(BadgeUpdateDto badgeUpdateDto);

    void deleteBadge(Long badgeId);
}
