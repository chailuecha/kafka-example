package cc.home.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@Profile("consumer")
class Consumer {
    private val logger = KotlinLogging.logger {}


    @KafkaListener(topics = ["big-topic"], groupId = "con-lots-of-partitions")
    fun consumeMessages(record: ConsumerRecord<ByteArray, ByteArray>) {
        logger.info { "Message: ${String(record.value())} , Offset: ${record.offset()}" }
    }
}
