package com.mulmeong.reward.common.config;

import com.mulmeong.event.comment.FeedCommentCreateEvent;
import com.mulmeong.event.comment.FeedRecommentCreateEvent;
import com.mulmeong.event.comment.ShortsCommentCreateEvent;
import com.mulmeong.event.comment.ShortsRecommentCreateEvent;
import com.mulmeong.event.member.MemberCreateEvent;
import com.mulmeong.reward.common.exception.BaseException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;


    /**
     * 아래 코드들은 이벤트가 생성될 때 마다 작성해야 하는 코드입니다.
     * 특정 이벤트의 메시지를 소비하는 Kafka Listener 컨테이너 팩토리를 생성합니다.
     *
     * @return {@link KafkaListenerContainerFactory} Kafka Listener가 사용하는 기본 Consumer Factory
     */

    /* 회원가입 후 이벤트 리스너 */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MemberCreateEvent> memberCreateEventListener() {
        return kafkaListenerContainerFactory(MemberCreateEvent.class);
    }

    /* 피드 댓글 생성 후 이벤트 리스너 */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FeedCommentCreateEvent> feedCommentCreateEventListener() {
        return kafkaListenerContainerFactory(FeedCommentCreateEvent.class);
    }

    /* 피드 대댓글 생성 후 이벤트 리스너 */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FeedRecommentCreateEvent> feedRecommentCreateEventListener() {
        return kafkaListenerContainerFactory(FeedRecommentCreateEvent.class);
    }

    /* 쇼츠 댓글 생성 후 이벤트 리스너 */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShortsCommentCreateEvent> shortsCommentCreateEventListener() {
        return kafkaListenerContainerFactory(ShortsCommentCreateEvent.class);
    }

    /* 쇼츠 대댓글 생성 후 이벤트 리스너 */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShortsRecommentCreateEvent> shortsRecommentCreateEventListener() {
        return kafkaListenerContainerFactory(ShortsRecommentCreateEvent.class);
    }

    // =================================================================
    // 아래는 기본 설정입니다.
    // =================================================================

    /**
     * 특정 메시지 타입에 맞게 ConsumerFactory를 생성합니다.
     *
     * @param messageType 제네릭으로 선언한 Event 객체
     * @return DefaultKafkaConsumerFactory, Kafka Listener가 사용하는 기본 Consumer Factory
     */
    @Bean
    public <T> ConsumerFactory<String, T> consumerFactory(Class<T> messageType) {
        return new DefaultKafkaConsumerFactory<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer,
                ConsumerConfig.GROUP_ID_CONFIG, groupId,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class.getName(),
                ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName(),
                JsonDeserializer.TRUSTED_PACKAGES, "com.mulmeong.event.*",
                JsonDeserializer.VALUE_DEFAULT_TYPE, messageType
        ));
    }

    /**
     * 위의 consumerFactory와 컨테이너 프로퍼티에 group-id 를 설정한 '컨테이너 팩토리'를 생성합니다.
     * 이 컨테이너 팩토리는 다중 스레드에서 Kafka 메시지를 처리하며, 이 설정을 통해 KafkaListener가 메시지를 소비합니다.
     *
     * @param messageType 제네릭으로 선언한 Event 객체
     * @return ConcurrentKafkaListenerContainerFactory, 다중 스레드에서 Kafka 메시지를 처리하는 컨테이너 팩토리
     */
    @Bean
    public <T> ConcurrentKafkaListenerContainerFactory<String, T> kafkaListenerContainerFactory(Class<T> messageType) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(messageType));
        factory.getContainerProperties().setGroupId(groupId);
        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                new FixedBackOff(1000L, 2)); // 1초 대기, 최대 2번 재시도
        errorHandler.addNotRetryableExceptions(BaseException.class);
        return errorHandler;
    }
}
