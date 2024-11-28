package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.point.application.MemberPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointKafkaConsumer {

    private final MemberPointService memberPointService;

    @KafkaListener(topics = "${event.member.pub.topics.member-create.name}",
            containerFactory = "memberCreateEventListener")
    public void handleMemberCreatedEvent(MemberCreateEvent event) {
        log.info("Consumed 회원 생성 이벤트 : {}", event);
        memberPointService.createMemberPointDocument(event);
    }
}
