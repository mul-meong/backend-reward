package com.mulmeong.event.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberGradeUpdateEvent {
    private String memberUuid;
    private Long gradeId;

    public static MemberGradeUpdateEvent of(String memberUuid, Long gradeId) {
        return MemberGradeUpdateEvent.builder()
                .memberUuid(memberUuid)
                .gradeId(gradeId)
                .build();
    }
}
