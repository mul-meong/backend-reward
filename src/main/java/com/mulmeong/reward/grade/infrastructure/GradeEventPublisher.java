package com.mulmeong.reward.grade.infrastructure;

import com.mulmeong.event.member.MemberGradeUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GradeEventPublisher {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${event.grade.pub.topics.member-grade-update.name}")
    private String memberGradeUpdateEventTopic;

    public void send(MemberGradeUpdateEvent event) {
        log.info("등급 업데이트 이벤트 발행: {}, topic: {}", event, memberGradeUpdateEventTopic);
        kafkaTemplate.send(memberGradeUpdateEventTopic, event);
    }
}
