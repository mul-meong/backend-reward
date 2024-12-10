package com.mulmeong.reward.badge.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor
@Getter
@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("뱃지 이름")
    @Column(nullable = false, length = 20)
    private String name;

    @Comment("뱃지 이미지 URL")
    @Column(nullable = false, length = 2083)
    private String imageUrl;

    @Comment("뱃지 설명")
    @Column(length = 100)
    private String description;

    @Builder
    public Badge(Long id, String name, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
