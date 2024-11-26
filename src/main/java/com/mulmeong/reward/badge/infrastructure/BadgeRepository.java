package com.mulmeong.reward.badge.infrastructure;

import com.mulmeong.reward.badge.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
