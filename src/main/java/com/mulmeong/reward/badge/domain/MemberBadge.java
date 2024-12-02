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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Comment("착용 여부")
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean equipped;

    @Builder
    public MemberBadge(Long id, String memberUuid, Badge badge, Boolean equipped) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.badge = badge;
        this.equipped = equipped;
    }
}

