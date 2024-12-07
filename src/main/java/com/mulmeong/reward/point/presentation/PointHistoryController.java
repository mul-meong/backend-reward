package com.mulmeong.reward.point.presentation;

import com.mulmeong.reward.common.response.BaseResponse;
import com.mulmeong.reward.point.application.PointHistoryService;
import com.mulmeong.reward.point.dto.CursorPage;
import com.mulmeong.reward.point.dto.in.PointHistoryFilterRequestDto;
import com.mulmeong.reward.point.dto.out.PointHistoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원-포인트 히스토리", description = "회원의 포인트 증가/감소 히스토리 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("auth/v1/member/{memberUuid}/point-history")
public class PointHistoryController {

    private final PointHistoryService pointHistoryService;

    @Operation(summary = "회원 포인트 히스토리 조회(페이지네이션) API", description = """
            memberUUID만 필수값이며, 나머지는 모두 선택 값입니다.<br>
            기본값 : sortBy=latest, pageSize = 20, pageNo = 0 <br>
            reason: `FEED_CREATE` / `SHORTS_CREATE` / `COMMENT_CREATE`
            / `CONTEST_WIN` / `CONTEST_JOIN` / `FEED_DELETED` /
            `SHORTS_DELETED` / `COMMENT_DELETED` <br>
            pointChangeType: `INCREASE` / `DECREASE`<br>
            sortBy: `latest`(최신순) / `oldest`(오래된 순)<br>
            """)
    @GetMapping
    public BaseResponse<CursorPage<PointHistoryDto>> getMemberPointHistories(
            @PathVariable String memberUuid,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) String pointChangeType,
            @Parameter(description = "정렬 기준", schema = @Schema(allowableValues = {"latest", "oldest"}))
            @RequestParam(defaultValue = "latest", value = "sortBy", required = false) String sortBy,
            @RequestParam(required = false) Long lastId,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNo
    ) {
        CursorPage<PointHistoryDto> cursorPage = pointHistoryService.getMemberPointHistories(
                PointHistoryFilterRequestDto.toDto(memberUuid, reason, pointChangeType,
                        sortBy, lastId, pageSize, pageNo));

        return new BaseResponse<>(cursorPage);
    }
}
