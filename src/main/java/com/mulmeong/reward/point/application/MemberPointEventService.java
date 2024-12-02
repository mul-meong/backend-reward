package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.point.domain.entity.EventType;

public interface MemberPointEventService {

    void createMemberPointDocument(MemberCreateEvent memberCreateEvent);

    void addPointByEvent(String memberUuid, EventType eventType);
}
