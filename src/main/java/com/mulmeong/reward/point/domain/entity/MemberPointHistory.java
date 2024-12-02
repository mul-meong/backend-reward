package com.mulmeong.reward.point.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MemberPointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원 UUID")
    @Column(nullable = false, length = 36)
    private String memberUuid;

    @Comment("포인트 변경 이유")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HistoryReason reason; // feed_create, comment_create, contest_win, etc

    @Comment("포인트 변경 타입")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private HistoryType historyType; // increase, decrease

    @Comment("변경된 포인트")
    @Column(nullable = false)
    private Integer point;

    @Comment("포인트 변경 일자")
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public MemberPointHistory(Long id, String memberUuid, HistoryReason reason,
                              HistoryType historyType, Integer point, LocalDateTime createdAt) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.reason = reason;
        this.historyType = historyType;
        this.point = point;
        this.createdAt = createdAt;
    }
}
