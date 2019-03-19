package cc.home.kafka

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener

@EnableKafka
@SpringBootApplication
class KafkaExampleApplication{
    private val logger = KotlinLogging.logger {}

    @KafkaListener(topics = ["test-topic"], groupId = "cc-spring-kafka-example")
    fun receive(record: ConsumerRecord<String, String>) {
        logger.info { "Process data: ${record.value()} , Offset: ${record.offset()}" }
    }
}

fun main(args: Array<String>) {
    runApplication<KafkaExampleApplication>(*args)
}
