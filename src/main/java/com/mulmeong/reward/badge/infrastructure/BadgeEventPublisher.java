package com.mulmeong.reward.badge.infrastructure;

import com.mulmeong.event.member.MemberBadgeUpdateEvent;
import com.mulmeong.event.member.MemberBadgeCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BadgeEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * send({토픽}, {이벤트}) 메서드 하나로 이벤트를 publish 할 수 있지만
     * 토픽을 지정하는 부분을 서비스의 비즈니스로직에서 분리하는것이 좋다고 판단해
     * 본 클래스에서 오버로딩하는 구조로 변경하였습니다.
     *
     * @author: 주성광
     */

    @Value("${event.badge.pub.topics.member-badge-update.name}")
    private String memberBadgeUpdateEventTopic;

    @Value("${event.badge.pub.topics.member-badge-create.name}")
    private String memberBadgeCreateEventTopic;

    public void send(String topic, Object event) {
        log.info("이벤트 발행, topic: {}", topic);
        kafkaTemplate.send(topic, event);
    }

    public void send(MemberBadgeUpdateEvent event) {
        kafkaTemplate.send(memberBadgeUpdateEventTopic, event);
    }

    public void send(MemberBadgeCreateEvent event) {
        kafkaTemplate.send(memberBadgeCreateEventTopic, event);
    }

}
