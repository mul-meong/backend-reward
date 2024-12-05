package com.mulmeong.reward.badge.infrastructure;

import com.mulmeong.event.contest.ContestVoteResultEvent;
import com.mulmeong.reward.badge.application.MemberBadgeEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BadgeEventConsumer {
    private final MemberBadgeEventService memberBadgeEventService;

    @KafkaListener(topics = "${event.contest.pub.topics.contest-result.name}",
            containerFactory = "memberCreateEventListener")
    public void handleContestWonEvent(ContestVoteResultEvent event) {
        memberBadgeEventService.createMemberBadge(event.getMemberUuid(), event.getBadgeId());
        log.info("Consumed 콘테스트 우승자 뱃지 생성 : {}", event);
    }
}
