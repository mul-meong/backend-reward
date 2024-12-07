package com.mulmeong.reward.badge.application;

import com.mulmeong.event.member.MemberBadgeCreateEvent;
import com.mulmeong.reward.badge.domain.Badge;
import com.mulmeong.reward.badge.domain.MemberBadge;
import com.mulmeong.reward.badge.dto.in.MemberBadgeCreateDto;
import com.mulmeong.reward.badge.infrastructure.BadgeEventPublisher;
import com.mulmeong.reward.badge.infrastructure.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberBadgeEventServiceImpl implements MemberBadgeEventService {

    private final BadgeEventPublisher eventPublisher;
    private final MemberBadgeRepository memberBadgeRepository;

    /**
     * 회원_뱃지 추가 이벤트 소비자.
     * 1. 특정 Badge관련 Event가 발행되면, 여기서 consume하여 뱃지를 부여.
     * 2. API를 통해 뱃지를 부여할 회원의 uuid와 뱃지의 id를 받아서 뱃지를 부여.
     * 3. 뱃지 부여시 알림을 위해 이벤트를 발행.
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMemberBadge(String memberUuid, Long badgeId) {
        MemberBadge memberBadge = memberBadgeRepository.save(toEntity(memberUuid, badgeId));
        eventPublisher.send(MemberBadgeCreateEvent.from(memberBadge, false));
    }

    // memberUUid와 badgeId를 받아 MemberBadge Entity로 변환
    private MemberBadge toEntity(String memberUuid, Long badgeId) {
        return MemberBadge.builder()
                .memberUuid(memberUuid)
                .badge(Badge.builder().id(badgeId).build())
                .equipped(false)
                .build();
    }
}
