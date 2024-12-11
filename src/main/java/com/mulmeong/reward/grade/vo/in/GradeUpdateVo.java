package com.mulmeong.reward.grade.vo.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class GradeUpdateVo {

    @NotBlank(message = "이름은 필수값입니다.")
    @Size(max = 10, message = "이름은 10자 이하여야 합니다.")
    private String name;

    @NotNull(message = "포인트 임계값은 필수값입니다.")
    @Min(value = 0, message = "포인트 임계값은 0 이상이어야 합니다.")
    private Integer pointThreshold;

    @NotBlank(message = "이미지 URL은 필수값입니다.")
    private String imageUrl;
}
