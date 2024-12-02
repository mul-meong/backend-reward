package com.mulmeong.reward.badge.application;

import com.mulmeong.reward.badge.dto.in.BadgeCreateDto;
import com.mulmeong.reward.badge.dto.in.BadgeUpdateDto;
import com.mulmeong.reward.badge.dto.out.BadgeDto;
import com.mulmeong.reward.badge.infrastructure.BadgeRepository;
import com.mulmeong.reward.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mulmeong.reward.common.response.BaseResponseStatus.NO_BADGE;


@RequiredArgsConstructor
@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    /* (관리자) 뱃지 생성 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBadge(BadgeCreateDto requestDto) {
        badgeRepository.save(requestDto.toEntity());
    }

    /* (관리자) 뱃지 수정 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBadge(BadgeUpdateDto requestDto) {
        badgeRepository.save(requestDto.toEntity(badgeRepository.findById(requestDto.getId())
                .orElseThrow(() -> new BaseException(NO_BADGE))));
    }

    /* (관리자) 뱃지 삭제 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBadge(Long badgeId) {
        badgeRepository.delete(badgeRepository.findById(badgeId)
                .orElseThrow(() -> new BaseException(NO_BADGE)));
    }

    /* 뱃지 단건 조회 */
    @Override
    @Transactional(readOnly = true)
    public BadgeDto getBadge(Long badgeId) {
        return BadgeDto.fromEntity(badgeRepository.findById(badgeId)
                .orElseThrow(() -> new BaseException(NO_BADGE)));
    }

    /* 뱃지 전체 조회 */
    @Override
    @Transactional(readOnly = true)
    public List<BadgeDto> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(BadgeDto::fromEntity)
                .toList();
    }
}
