package com.mulmeong.reward.point.presentation;

import com.mulmeong.reward.common.response.BaseResponse;
import com.mulmeong.reward.point.application.MemberPointService;
import com.mulmeong.reward.point.dto.out.MemberPointDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원-포인트 API", description = "회원의 포인트 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/member")
public class MemberPointController {
    // note: 프로필 화면단에서 본인만 point를 확인한다면 /auth/v1/member/point로 변경 필요

    private final MemberPointService memberPointService;

    @Operation(summary = "특정 회원 포인트 조회",
            description = "포인트의 경우 수정이 잦아 Member-Read Db에 저장하지 않아, 해당 API를 통해 조회합니다.")
    @GetMapping("/{memberUuid}/point")
    public BaseResponse<MemberPointDto> getMemberPoint(@PathVariable String memberUuid) {
        return new BaseResponse<>(memberPointService.getMemberPoint(memberUuid));
    }
}
