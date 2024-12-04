package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.event.comment.*;
import com.mulmeong.event.contest.ContestPostCreateEvent;
import com.mulmeong.event.feed.FeedCreateEvent;
import com.mulmeong.event.feed.FeedDeleteEvent;
import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.event.report.ReportApproveEvent;
import com.mulmeong.event.shorts.ShortsCreateEvent;
import com.mulmeong.reward.point.application.MemberPointEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.mulmeong.reward.point.domain.entity.EventType.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointKafkaConsumer {

    private final MemberPointEventService memberPointEventService;

    @KafkaListener(topics = "${event.member.pub.topics.member-create.name}",
            containerFactory = "memberCreateEventListener")
    public void handleMemberCreatedEvent(MemberCreateEvent event) {
        memberPointEventService.createMemberPointDocument(event);
        log.info("Consumed 회원 생성 이벤트 : {}", event);
    }

    // =================== 피드 ===================
    @KafkaListener(topics = "${event.feed.pub.topics.feed-create.name}",
            containerFactory = "feedCreateEventListener")
    public void handleFeedCreatedEvent(FeedCreateEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), FEED_CREATED);
        log.info("Consumed 피드 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.feed.pub.topics.feed-delete.name}",
            containerFactory = "feedCreateEventListener")
    public void handleFeedUpdatedEvent(FeedDeleteEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), FEED_DELETED);
        log.info("Consumed 피드 삭제 이벤트 : {}", event);
    }

    // =================== 숏츠 ===================
    @KafkaListener(topics = "${event.shorts.pub.topics.shorts-create.name}",
            containerFactory = "shortsCreateEventListener")
    public void handleShortsCreatedEvent(ShortsCreateEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), SHORTS_CREATED);
        log.info("Consumed 숏츠 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.shorts.pub.topics.shorts-delete.name}",
            containerFactory = "shortsDeleteEventListener")
    public void handleShortsDeletedEvent(ShortsCreateEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), SHORTS_DELETED);
        log.info("Consumed 숏츠 삭제 이벤트 : {}", event);
    }

    // =================== 댓글 ===================
    @KafkaListener(topics = "${event.comment.pub.topics.feed-comment-create.name}",
            containerFactory = "feedCommentCreateEventListener")
    public void handleFeedCommentCreatedEvent(FeedCommentCreateEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), COMMENT_CREATED);
        log.info("Consumed 피드 댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.feed-recomment-create.name}",
            containerFactory = "feedRecommentCreateEventListener")
    public void handleFeedRecommentCreatedEvent(FeedRecommentCreateEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), COMMENT_CREATED);
        log.info("Consumed 피드 대댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.shorts-comment-create.name}",
            containerFactory = "shortsCommentCreateEventListener")
    public void handleShortsCommentCreatedEvent(ShortsCommentCreateEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), COMMENT_CREATED);
        log.info("Consumed 숏츠 댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.shorts-recomment-create.name}",
            containerFactory = "shortsRecommentCreateEventListener")
    public void handleShortsRecommentCreatedEvent(ShortsRecommentCreateEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), COMMENT_CREATED);
        log.info("Consumed 숏츠 대댓글 생성 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.feed-comment-delete.name}",
            containerFactory = "feedCommentDeleteEventListener")
    public void handleFeedCommentDeletedEvent(FeedCommentDeleteEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), COMMENT_DELETED);
        log.info("Consumed 피드 댓글 삭제 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.feed-recomment-delete.name}",
            containerFactory = "feedRecommentDeleteEventListener")
    public void handleFeedRecommentDeleteEvent(FeedRecommentDeleteEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), COMMENT_DELETED);
        log.info("Consumed 피드 대댓글 삭제 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.shorts-comment-delete.name}",
            containerFactory = "shortsCommentDeleteEventListener")
    public void handleShortsCommentDeletedEvent(ShortsCommentDeleteEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), COMMENT_DELETED);
        log.info("Consumed 숏츠 댓글 삭제 이벤트 : {}", event);
    }

    @KafkaListener(topics = "${event.comment.pub.topics.shorts-recomment-delete.name}",
            containerFactory = "shortsRecommentDeleteEventListener")
    public void handleShortsRecommentDeletedEvent(ShortsRecommentDeleteEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), COMMENT_DELETED);
        log.info("Consumed 숏츠 대댓글 삭제 이벤트 : {}", event);
    }

    // =================== 콘테스트 ===================
    @KafkaListener(topics = "${event.contest.pub.topics.contest-post-create.name}",
            containerFactory = "contestPostCreateEventListener")
    public void handleContestPostCreatedEvent(ContestPostCreateEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), CONTEST_JOINED);
        log.info("Consumed 콘테스트 포스트 생성 이벤트 : {}", event);
    }

    // =================== 신고 ===================
    @KafkaListener(topics = "${event.report.pub.topics.report-approve.name}",
            containerFactory = "reportApproveEventListener")
    public void handleReportApproveEvent(ReportApproveEvent event) {
        memberPointEventService.updatePointByEvent(event.getMemberUuid(), REPORT_APPROVED);
        log.info("Consumed 신고 승인 이벤트 : {}", event);
    }
}
