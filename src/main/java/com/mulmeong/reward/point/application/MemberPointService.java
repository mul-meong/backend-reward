package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;

public interface MemberPointService {

    void createMemberPointDocument(MemberCreateEvent memberCreateEvent);
}
