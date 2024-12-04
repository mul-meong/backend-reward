package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.reward.point.dto.CursorPage;
import com.mulmeong.reward.point.dto.in.PointHistoryFilterRequestDto;
import com.mulmeong.reward.point.dto.out.PointHistoryDto;

public interface PointHistoryRepositoryCustom {

    CursorPage<PointHistoryDto> getMemberPointHistories(PointHistoryFilterRequestDto requestDto);
}
