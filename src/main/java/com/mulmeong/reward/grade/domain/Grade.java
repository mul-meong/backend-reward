package com.mulmeong.reward.grade.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
public class Grade {

    @Comment("ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("등급 이름")
    @Column(nullable = false, unique = true, length = 5)
    private String name;

    @Comment("등급에 해당하는 포인트 임계값")
    @Column(nullable = false)
    private Integer pointThreshold;

    @Builder
    public Grade(Long id, String name, Integer pointThreshold) {
        this.id = id;
        this.name = name;
        this.pointThreshold = pointThreshold;
    }
}
