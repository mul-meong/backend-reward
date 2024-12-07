package com.mulmeong.reward.point.application;

import com.mulmeong.reward.point.domain.entity.PointHistory;
import com.mulmeong.reward.point.dto.CursorPage;
import com.mulmeong.reward.point.dto.in.PointHistoryFilterRequestDto;
import com.mulmeong.reward.point.dto.out.PointHistoryDto;

public interface PointHistoryService {
    CursorPage<PointHistoryDto> getMemberPointHistories(PointHistoryFilterRequestDto requestDto);
}
