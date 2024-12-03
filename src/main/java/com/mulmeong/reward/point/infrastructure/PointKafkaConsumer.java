package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.event.comment.FeedCommentCreateEvent;
import com.mulmeong.event.comment.FeedRecommentCreateEvent;
import com.mulmeong.event.comment.ShortsCommentCreateEvent;
import com.mulmeong.event.comment.ShortsRecommentCreateEvent;
import com.mulmeong.event.contest.ContestPostCreateEvent;
import com.mulmeong.event.feed.FeedCreateEvent;
import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.event.shorts.ShortsCreateEvent;
import com.mulmeong.reward.point.application.MemberPointEventService;
import com.mulmeong.reward.point.domain.entity.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.mulmeong.reward.point.domain.entity.EventType.COMMENT_CREATED;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointKafkaConsumer {

    private final MemberPointEventService memberPointEventService;

    @Value("${event.feed.pub.topics.feed-create.name}")
    private String testTopic;

    @KafkaListener(topics = "${event.member.pub.topics.member-create.name}",
            containerFactory = "memberCreateEventListener")
    public void handleMemberCreatedEvent(MemberCreateEvent event) {
        memberPointEventService.createMemberPointDocument(event);
        log.info("Consumed 회원 생성 이벤트 : {}", event);
    }

    /* 댓글 작성시 포인트 증가(피드|숏츠 * 댓글|대댓글 총 4종류 */
    @KafkaListener(topics = "${event.comment.pub.topics.feed-comment-create.name}",
            containerFactory = "feedCommentCreateEventListener")
    public void handleFeedCommentCreatedEvent(FeedCommentCreateEvent event) {
        memberPointEventService.addPointByEvent(event.getMemberUuid(), COMMENT_CREATED);
        log.info("Consumed 피드 댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.feed-recomment-create.name}",
            containerFactory = "feedRecommentCreateEventListener")
    public void handleFeedRecommentCreatedEvent(FeedRecommentCreateEvent event) {
        memberPointEventService.addPointByEvent(event.getMemberUuid(), COMMENT_CREATED);
        log.info("Consumed 피드 대댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.shorts-comment-create.name}",
            containerFactory = "shortsCommentCreateEventListener")
    public void handleShortsCommentCreatedEvent(ShortsCommentCreateEvent event) {
        memberPointEventService.addPointByEvent(event.getMemberUuid(), COMMENT_CREATED);
        log.info("Consumed 숏츠 댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.shorts-recomment-create.name}",
            containerFactory = "shortsRecommentCreateEventListener")
    public void handleShortsRecommentCreatedEvent(ShortsRecommentCreateEvent event) {
        memberPointEventService.addPointByEvent(event.getMemberUuid(), COMMENT_CREATED);
        log.info("Consumed 숏츠 대댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.feed.pub.topics.feed-create.name}",
            containerFactory = "feedCreateEventListener")
    public void handleFeedCreatedEvent(FeedCreateEvent event) {
        memberPointEventService.addPointByEvent(event.getMemberUuid(), EventType.FEED_CREATED);
        log.info("Consumed 피드 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.shorts.pub.topics.shorts-create.name}",
            containerFactory = "shortsCreateEventListener")
    public void handleShortsCreatedEvent(ShortsCreateEvent event) {
        memberPointEventService.addPointByEvent(event.getMemberUuid(), EventType.SHORTS_CREATED);
        log.info("Consumed 숏츠 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.contest.pub.topics.contest-post-create.name}",
            containerFactory = "contestPostCreateEventListener")
    public void handleContestPostCreatedEvent(ContestPostCreateEvent event) {
        memberPointEventService.addPointByEvent(event.getMemberUuid(), EventType.CONTEST_JOINED);
        log.info("Consumed 콘테스트 포스트 생성 이벤트 : {}", event);
    }
}
