package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.reward.point.domain.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}
