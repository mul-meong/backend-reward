package com.mulmeong.reward.badge.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"memberUuid", "badgeId"})})
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
    private Long badgeId;

    @Comment("착용 여부")
    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean equipped;

    @Builder
    public MemberBadge(Long id, String memberUuid, Long badgeId, Boolean equipped) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.badgeId = badgeId;
        this.equipped = equipped;
    }
}

