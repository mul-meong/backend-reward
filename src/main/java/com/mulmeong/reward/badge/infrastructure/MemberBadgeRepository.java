package com.mulmeong.reward.badge.infrastructure;

import com.mulmeong.reward.badge.domain.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    Optional<MemberBadge> findByMemberUuidAndEquipped(String memberUuid, Boolean equipped);

    Optional<MemberBadge> findByMemberUuidAndBadgeId(String memberUuid, Long badgeId);

    List<MemberBadge> findAllByMemberUuid(String memberUuid);
}
