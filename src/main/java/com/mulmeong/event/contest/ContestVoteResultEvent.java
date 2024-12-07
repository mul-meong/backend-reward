package com.mulmeong.event.contest;

import lombok.Getter;

@Getter
public class ContestVoteResultEvent {
    private String memberUuid;
    private Long badgeId;
}
