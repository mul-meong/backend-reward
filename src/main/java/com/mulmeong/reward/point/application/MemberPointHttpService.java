package com.mulmeong.reward.point.application;

import com.mulmeong.reward.point.dto.out.MemberPointDto;

public interface MemberPointHttpService {

    MemberPointDto getMemberPoint(String memberUuid);
}
