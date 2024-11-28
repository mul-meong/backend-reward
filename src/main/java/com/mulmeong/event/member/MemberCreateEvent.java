package com.mulmeong.event.member;

import com.mulmeong.reward.point.domain.document.MemberPoint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateEvent {

    private String memberUuid;

    public MemberPoint toEntity() {
        return MemberPoint.builder()
                .memberUuid(memberUuid)
                .point(0) // 0점으로 시작
                .build();
    }
}
