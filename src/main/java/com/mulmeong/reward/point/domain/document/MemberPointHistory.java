package com.mulmeong.reward.point.domain.document;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class MemberPointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Comment("회원 UUID")
    @Column(nullable = false, length = 36)
    private String memberUuid;

    @Comment("포인트 변경 이유")
    @Column(nullable = false, length = 30)
    private String reason; // feed_create, comment_create, contest_win, etc

    @Comment("포인트 변경 타입")
    @Column(nullable = false, length = 10)
    private String historyType; // increase, decrease

    @Comment("변경된 포인트")
    @Column(nullable = false)
    private Integer point;

    @Comment("포인트 변경 일자")
    @Column(nullable = false)
    private LocalDate createdAt;

    @Builder
    public MemberPointHistory(String id, String memberUuid, String reason,
                              String historyType, Integer point, LocalDate createdAt) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.reason = reason;
        this.historyType = historyType;
        this.point = point;
        this.createdAt = createdAt;
    }
}
