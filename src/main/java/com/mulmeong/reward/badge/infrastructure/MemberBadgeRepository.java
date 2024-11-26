package com.mulmeong.reward.badge.infrastructure;

import com.mulmeong.reward.badge.domain.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {
}
