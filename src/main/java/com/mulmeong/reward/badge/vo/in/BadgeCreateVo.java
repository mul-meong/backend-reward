package com.mulmeong.reward.badge.vo.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BadgeCreateVo {

    @NotBlank(message = "뱃지 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "뱃지 이미지 URL은 필수입니다.")
    private String imageUrl;

    @NotBlank(message = "뱃지 설명은 필수입니다.")
    private String description;

}
