package com.mulmeong.reward.badge.presentation;

import com.mulmeong.reward.badge.application.BadgeService;
import com.mulmeong.reward.badge.dto.BadgeDto;
import com.mulmeong.reward.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "뱃지 API", description = "뱃지 API")
@RequestMapping("/v1/badge")
@RequiredArgsConstructor
@RestController
public class BadgeController {

    private final BadgeService badgeService;

    @Operation(summary = "뱃지 ID로 단건 조회")
    @GetMapping("/{badgeId}")
    public BaseResponse<BadgeDto> getBadge(@PathVariable Long badgeId) {

        return new BaseResponse<>(badgeService.getBadge(badgeId));
    }

    // 뱃지 갯수가 많아지면 페이징 처리 필요
    @Operation(summary = "모든 뱃지 조회", description = "QUA의 모든 뱃지를 조회하는 페이지에서 사용")
    @GetMapping
    public BaseResponse<List<BadgeDto>> getAllBadges() {
        return new BaseResponse<>(badgeService.getAllBadges());
    }
}
