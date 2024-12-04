package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.point.domain.entity.EventType;

public interface MemberPointEventService {

    void createMemberPointDocument(MemberCreateEvent memberCreateEvent);

    boolean updatePointByEvent(String memberUuid, EventType eventType);
}
