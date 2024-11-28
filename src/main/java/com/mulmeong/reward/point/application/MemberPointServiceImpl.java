package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.point.infrastructure.MemberPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberPointServiceImpl implements MemberPointService {

    private final MemberPointRepository memberPointRepository;

    @Override
    public void createMemberPointDocument(MemberCreateEvent event) {
        memberPointRepository.save(event.toEntity());
    }
}
