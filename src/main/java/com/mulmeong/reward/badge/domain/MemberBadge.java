package com.mulmeong.reward.badge.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor
@Getter
@Entity
public class MemberBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원 UUID")
    @Column(nullable = false, length = 36)
    private String memberUuid;

    @Comment("뱃지 ID")
    @Column(nullable = false)
    private Integer badgeId;

    @Comment("착용 여부")
    @Column(nullable = false)
    private boolean isEquipped;

    @Builder
    public MemberBadge(Long id, String memberUuid, Integer badgeId, boolean isEquipped) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.badgeId = badgeId;
        this.isEquipped = isEquipped;
    }
}

