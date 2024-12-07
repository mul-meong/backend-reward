package com.mulmeong.event.feed;

import lombok.Data;
import lombok.Getter;

@Getter
public class FeedCreateEvent {
    private String memberUuid;
}

