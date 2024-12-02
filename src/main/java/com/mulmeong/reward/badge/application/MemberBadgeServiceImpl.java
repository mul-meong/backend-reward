package com.mulmeong.reward.badge.application;

import com.mulmeong.event.member.MemberBadgeUpdateEvent;
import com.mulmeong.reward.badge.domain.MemberBadge;
import com.mulmeong.reward.badge.dto.in.MemberBadgeCreateDto;
import com.mulmeong.reward.badge.dto.in.MemberBadgeUpdateDto;
import com.mulmeong.reward.badge.dto.out.MemberBadgeDto;
import com.mulmeong.reward.badge.infrastructure.BadgeEventPublisher;
import com.mulmeong.reward.badge.infrastructure.MemberBadgeRepository;
import com.mulmeong.reward.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mulmeong.reward.common.response.BaseResponseStatus.NO_BADGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberBadgeServiceImpl implements MemberBadgeService {

    private final MemberBadgeRepository memberBadgeRepository;

    private final BadgeEventPublisher eventPublisher;

    /**
     * 회원_뱃지 수정 API(회원이 뱃지 장착/해제)
     * 관련 정책: 회원은 최대 하나의 뱃지를 장착 가능.
     * 요청이 장착인 경우, 기존 장착 뱃지는 해제하고, 요청 뱃지를 장착하며,
     * 요청이 해제인 경우, 요청 뱃지의 장착 상태를 해제합니다.
     * 그 후 이벤트를 발행해 ReadDB에 반영합니다.
     *
     * @param requestDto 회원uuid, 뱃지id, 장착 여부
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBadgeEquipStatus(MemberBadgeUpdateDto requestDto) {

        String memberUuid = requestDto.getMemberUuid();
        boolean isEquipped = requestDto.getEquipped();

        if (isEquipped) {
            unEquipExistingBadge(memberUuid);
        }

        MemberBadge memberBadge = memberBadgeRepository.findByMemberUuidAndBadgeId(memberUuid, requestDto.getBadgeId())
                .orElseThrow(() -> new BaseException(NO_BADGE));

        // 요청대로 뱃지 장착/해제 상태 변경
        updateEquippedStatus(memberBadge, isEquipped);
        eventPublisher.send(MemberBadgeUpdateEvent.from(memberBadge, isEquipped));
    }

    /**
     * 회원이 가진 특정 뱃지 조회.
     *
     * @return 회원이 가진 특정 뱃지(ID, 장착 여부)
     */
    @Override
    @Transactional(readOnly = true)
    public MemberBadgeDto getMemberBadge(String memberUuid, Long badgeId) {

        return MemberBadgeDto.fromEntity(memberBadgeRepository.findByMemberUuidAndBadgeId(memberUuid, badgeId)
                .orElseThrow(() -> new BaseException(NO_BADGE)));
    }

    /**
     * 회원이 가진 뱃지 목록 조회.
     *
     * @return 회원이 가진 뱃지 목록(ID, 장착 여부)
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberBadgeDto> getMemberBadges(String memberUuid) {

        return memberBadgeRepository.findAllByMemberUuid(memberUuid).stream()
                .map(MemberBadgeDto::fromEntity)
                .toList();
    }


    /**
     * 회원_뱃지 추가 API 겸 이벤트 소비자.
     * 1. 특정 Badge관련 Event가 발행되면, 여기서 consume하여 뱃지를 부여.
     * 2. API를 통해 뱃지를 부여할 회원의 uuid와 뱃지의 id를 받아서 뱃지를 부여.
     *
     * @param requestDto 회원uuid, 뱃지id, 장착 여부(false)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMemberBadge(MemberBadgeCreateDto requestDto) {
        memberBadgeRepository.save(requestDto.toEntity());
    }

    //// private 메서드

    // 이미 장착된 뱃지가 있으면 해제하는 메서드
    private void unEquipExistingBadge(String memberUuid) {
        memberBadgeRepository.findByMemberUuidAndEquipped(memberUuid, true)
                .ifPresent(existingBadge -> updateEquippedStatus(existingBadge, false));
    }

    // 회원 뱃지의 장착 상태를 수정하는 private 메서드
    private void updateEquippedStatus(MemberBadge memberBadge, boolean isEquipped) {
        memberBadgeRepository.save(MemberBadge.builder()
                .id(memberBadge.getId())
                .memberUuid(memberBadge.getMemberUuid())
                .badge(memberBadge.getBadge())
                .equipped(isEquipped)
                .build());
    }
}
