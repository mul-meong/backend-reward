package com.mulmeong.reward.point.dto.out;

import com.mulmeong.reward.point.domain.document.MemberPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberPointDto {

    private Integer point;

    public static MemberPointDto from(MemberPoint memberPoint) {
        return MemberPointDto.builder()
                .point(memberPoint.getPoint())
                .build();
    }
}
