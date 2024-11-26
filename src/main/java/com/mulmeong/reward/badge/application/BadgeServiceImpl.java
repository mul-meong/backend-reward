package com.mulmeong.reward.badge.application;

import com.mulmeong.reward.badge.dto.BadgeDto;
import com.mulmeong.reward.badge.dto.in.BadgeCreateDto;
import com.mulmeong.reward.badge.dto.in.BadgeUpdateDto;
import com.mulmeong.reward.badge.infrastructure.BadgeRepository;
import com.mulmeong.reward.badge.infrastructure.MemberBadgeRepository;
import com.mulmeong.reward.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mulmeong.reward.common.response.BaseResponseStatus.NO_BADGE;


@RequiredArgsConstructor
@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;


    @Override
    public void createBadge(BadgeCreateDto requestDto) {
        badgeRepository.save(requestDto.toEntity());
    }

    @Override
    public BadgeDto getBadge(Long badgeId) {
        return BadgeDto.fromEntity(badgeRepository.findById(badgeId)
                .orElseThrow(() -> new BaseException(NO_BADGE)));
    }

    @Override
    public List<BadgeDto> getAllBadges() {
        return badgeRepository.findAll().stream().map(BadgeDto::fromEntity).toList();

    }

    @Override
    public void updateBadge(BadgeUpdateDto requestDto) {
        badgeRepository.save(requestDto.toEntity(badgeRepository.findById(requestDto.getId())
                .orElseThrow(() -> new BaseException(NO_BADGE))));
    }

    @Override
    public void deleteBadge(Long badgeId) {
        badgeRepository.delete(badgeRepository.findById(badgeId)
                .orElseThrow(() -> new BaseException(NO_BADGE)));
    }
}
