package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;

public interface MemberPointEventService {

    void createMemberPointDocument(MemberCreateEvent memberCreateEvent);

    void addPointByComment(String memberUuid);
}
