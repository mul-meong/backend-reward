package com.mulmeong.reward.grade.vo.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GradeCreateVo {

    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    @NotBlank(message = "포인트 임계값은 필수값입니다.")
    private Integer pointThreshold;

    @NotBlank(message = "이미지 URL은 필수값입니다.")
    private String imageUrl;
}
