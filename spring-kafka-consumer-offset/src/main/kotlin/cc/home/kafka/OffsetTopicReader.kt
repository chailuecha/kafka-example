package cc.home.kafka

import kafka.coordinator.group.GroupMetadataManager
import kafka.coordinator.group.OffsetKey
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.nio.ByteBuffer

const val INTERNAL_OFFSET_TOPIC = "__consumer_offsets"

@Component
@Profile("offset-topic-reader")
class OffsetTopicReader {
    private val logger = KotlinLogging.logger {}

    @KafkaListener(topics = [INTERNAL_OFFSET_TOPIC], groupId = "con-offset-topic")
    fun consumeOffsetTopic(record: ConsumerRecord<ByteArray, ByteArray>) {
        val baseKey = GroupMetadataManager.readMessageKey(ByteBuffer.wrap(record.key()))

        when (baseKey is OffsetKey) {
            true -> {
                val tp = baseKey.key().topicPartition()
                when (tp.topic() == "big-topic") {
                    true -> logger.info { "TopicPartition: $tp , commit-offset: ${getCommitOffset(record.value())}" }
                }
            }
        }
    }

    private fun getCommitOffset(commitMessageBytes: ByteArray): Long {
        return GroupMetadataManager
            .readOffsetMessageValue(ByteBuffer.wrap(commitMessageBytes))
            .offset()
    }
}


