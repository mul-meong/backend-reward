package com.mulmeong.reward.badge.presentation;

import com.mulmeong.reward.badge.application.BadgeService;
import com.mulmeong.reward.badge.dto.in.BadgeCreateDto;
import com.mulmeong.reward.badge.dto.in.BadgeUpdateDto;
import com.mulmeong.reward.badge.vo.in.BadgeCreateVo;
import com.mulmeong.reward.badge.vo.in.BadgeUpdateVo;
import com.mulmeong.reward.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 전용 뱃지 API 컨트롤러, 관리자 권한 필요.
 * 뱃지 생성, 수정, 삭제.
 */
@Tag(name = "뱃지 API", description = "뱃지 API")
@RequestMapping("/admin/v1/badge")
@RequiredArgsConstructor
@RestController
public class AdminBadgeController {

    private final BadgeService badgeService;

    @Operation(summary = "(관리자) 뱃지 생성", description = "모든 필드는 NotBlank")
    @PostMapping
    public BaseResponse<Void> createBadge(@RequestBody @Valid BadgeCreateVo requestVo) {
        badgeService.createBadge(BadgeCreateDto.toDto(requestVo));
        return new BaseResponse<>();
    }

    @Operation(summary = "(관리자) 뱃지 수정", description = "모든 필드는 NotBlank")
    @PutMapping("/{badgeId}")
    public BaseResponse<Void> updateBadge(@PathVariable @Valid Long badgeId,
                                          @RequestBody BadgeUpdateVo requestVo) {
        badgeService.updateBadge(BadgeUpdateDto.toDto(requestVo, badgeId));
        return new BaseResponse<>();
    }

    @Operation(summary = "(관리자) 뱃지 삭제")
    @DeleteMapping("/{badgeId}")
    public BaseResponse<Void> deleteBadge(@PathVariable Long badgeId) {
        badgeService.deleteBadge(badgeId);
        return new BaseResponse<>();
    }
}
