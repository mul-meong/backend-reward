package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.point.dto.out.MemberPointDto;

public interface MemberPointService {

    // Http 요청을 처리하는 메서드
    MemberPointDto getMemberPoint(String memberUuid);

    // kafka로 받은 이벤트를 처리하는 메서드
    void createMemberPointDocument(MemberCreateEvent memberCreateEvent);
}
