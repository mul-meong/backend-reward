package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.reward.point.domain.entity.MemberPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPointHistoryRepository extends JpaRepository<MemberPointHistory, Long> {
}
