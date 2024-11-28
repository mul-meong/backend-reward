package com.mulmeong.reward.point.application;

import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.point.infrastructure.MemberPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPointEventServiceImpl implements MemberPointEventService {

    private final MemberPointRepository memberPointRepository;

    @Override
    public void createMemberPointDocument(MemberCreateEvent event) {
        memberPointRepository.save(event.toEntity());
    }
}
