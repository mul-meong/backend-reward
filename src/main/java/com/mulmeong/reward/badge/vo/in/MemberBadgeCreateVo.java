package com.mulmeong.reward.badge.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberBadgeCreateVo {

    @NotNull(message = "badgeId는 필수값입니다.")
    private Long badgeId;
}
