package example.com.services

import example.com.domains.Event
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.UUID

class EventService(private val kafkaProducer: KafkaProducer<Long, String>) {

    suspend fun sendEvent(event: Event) =
        kafkaProducer.send(ProducerRecord("events", 0, event.toString()))

}