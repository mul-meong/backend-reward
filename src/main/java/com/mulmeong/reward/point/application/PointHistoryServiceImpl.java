package com.mulmeong.reward.point.application;

import com.mulmeong.reward.point.domain.entity.PointHistory;
import com.mulmeong.reward.point.dto.CursorPage;
import com.mulmeong.reward.point.dto.in.PointHistoryFilterRequestDto;
import com.mulmeong.reward.point.dto.out.PointHistoryDto;
import com.mulmeong.reward.point.infrastructure.PointHistoryRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointHistoryRepositoryCustom pointHistoryRepositoryCustom;

    @Transactional(readOnly = true)
    @Override
    public CursorPage<PointHistoryDto> getMemberPointHistories(PointHistoryFilterRequestDto requestDto) {

        return pointHistoryRepositoryCustom.getMemberPointHistories(requestDto);
    }
}
