package com.mulmeong.reward.badge.presentation;

import com.mulmeong.reward.badge.application.MemberBadgeService;
import com.mulmeong.reward.badge.dto.in.MemberBadgeCreateDto;
import com.mulmeong.reward.badge.dto.in.MemberBadgeUpdateDto;
import com.mulmeong.reward.badge.dto.out.MemberBadgeDto;
import com.mulmeong.reward.badge.vo.in.MemberBadgeCreateVo;
import com.mulmeong.reward.badge.vo.in.MemberBadgeUpdateVo;
import com.mulmeong.reward.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "회원-뱃지 API", description = "회원이 가진 뱃지 관련한 API(회원만 사용가능)")
@RequestMapping("/auth/v1/members/{memberUuid}/badges")
@RequiredArgsConstructor
@RestController
public class AuthMemberBadgeController {

    private final MemberBadgeService memberBadgeService;

    @Operation(summary = "회원_뱃지 생성", description = """
            회원이 뱃지를 획득하는 API입니다. 뱃지는 한 회원당 하나씩 가질 수 있으며
            뱃지는 획득 시 장착되지 않은 상태로 생성됩니다.
            """)
    @PostMapping()
    public BaseResponse<Void> createMemberBadge(@PathVariable String memberUuid,
                                                @RequestBody MemberBadgeCreateVo requestVo) {
        memberBadgeService.createMemberBadge(MemberBadgeCreateDto.toDto(requestVo, memberUuid));
        return new BaseResponse<>();
    }

    @Operation(summary = "회원_뱃지 수정", description = """
            회원이 뱃지를 장착/해제하는 API입니다. 회원은 최대 하나의 뱃지를 장착 가능하기 때문에,
            장착 요청이 들어올 경우(equipped가 true인 경우), 기존 장착 뱃지는 해제되며,
            장착 요청이 해제인 경우(equipped가 false인 경우), 장착 뱃지가 없는 상태로 변경됩니다.
            """)
    @PutMapping("/{badgeId}/equip")
    public BaseResponse<Void> updateBadgeEquipStatus(@PathVariable String memberUuid,
                                                     @PathVariable Long badgeId,
                                                     @RequestBody MemberBadgeUpdateVo requestVo) {
        memberBadgeService.updateBadgeEquipStatus(
                MemberBadgeUpdateDto.toDto(requestVo, memberUuid, badgeId));
        return new BaseResponse<>();
    }

    @Operation(summary = "특정 회원이 가진 특정 뱃지 조회")
    @GetMapping("/{badgeId}")
    public BaseResponse<MemberBadgeDto> getBadge(@PathVariable String memberUuid, @PathVariable Long badgeId) {
        return new BaseResponse<>(memberBadgeService.getMemberBadge(memberUuid, badgeId));
    }

    @Operation(summary = "회원이 가진 모든 뱃지 조회")
    @GetMapping()
    public BaseResponse<List<MemberBadgeDto>> getAllBadges(@PathVariable String memberUuid) {
        return new BaseResponse<>(memberBadgeService.getMemberBadges(memberUuid));
    }


}
