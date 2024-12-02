package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.event.comment.FeedCommentCreateEvent;
import com.mulmeong.event.comment.FeedRecommentCreateEvent;
import com.mulmeong.event.comment.ShortsCommentCreateEvent;
import com.mulmeong.event.comment.ShortsRecommentCreateEvent;
import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.point.application.MemberPointEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointKafkaConsumer {

    private final MemberPointEventService memberPointEventService;

    @KafkaListener(topics = "${event.member.pub.topics.member-create.name}",
            containerFactory = "memberCreateEventListener")
    public void handleMemberCreatedEvent(MemberCreateEvent event) {
        log.info("Consumed 회원 생성 이벤트 : {}", event);
        memberPointEventService.createMemberPointDocument(event);
    }

    /* 댓글 작성시 포인트 증가(피드|숏츠 * 댓글|대댓글 총 4종류 */
    @KafkaListener(topics = "${event.comment.pub.topics.feed-comment-create.name}",
            containerFactory = "feedCommentCreateEventListener")
    public void handleFeedCommentCreatedEvent(FeedCommentCreateEvent event) {
        memberPointEventService.addPointByComment(event.getMemberUuid());
    }

    @KafkaListener(topics = "${event.comment.pub.topics.feed-recomment-create.name}",
            containerFactory = "feedRecommentCreateEventListener")
    public void handleFeedRecommentCreatedEvent(FeedRecommentCreateEvent event) {
        memberPointEventService.addPointByComment(event.getMemberUuid());
        log.info("Consumed 피드 대댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.shorts-comment-create.name}",
            containerFactory = "shortsCommentCreateEventListener")
    public void handleShortsCommentCreatedEvent(ShortsCommentCreateEvent event) {
        memberPointEventService.addPointByComment(event.getMemberUuid());
        log.info("Consumed 숏츠 댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.shorts-recomment-create.name}",
            containerFactory = "shortsRecommentCreateEventListener")
    public void handleShortsRecommentCreatedEvent(ShortsRecommentCreateEvent event) {
        memberPointEventService.addPointByComment(event.getMemberUuid());
        log.info("Consumed 숏츠 대댓글 생성 이벤트 : {}", event);
    }

    /* */
}
