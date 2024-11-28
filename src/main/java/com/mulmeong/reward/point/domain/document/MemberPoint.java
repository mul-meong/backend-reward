package com.mulmeong.reward.point.domain.document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "member_point")
public class MemberPoint {

    @Id
    private String memberUuid;
    private Integer point;

    @Builder
    public MemberPoint(String memberUuid, Integer point) {
        this.memberUuid = memberUuid;
        this.point = point;
    }
}
