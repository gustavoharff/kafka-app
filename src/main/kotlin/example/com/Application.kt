package example.com

import example.com.domains.Event
import example.com.plugins.*
import example.com.services.EventService
import io.ktor.server.application.*
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.Properties

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun <K, V> buildProducer(environment: ApplicationEnvironment): KafkaProducer<K, V> {
    val producerConfig = environment.config.config("ktor.kafka.producer")
    val producerProps = Properties().apply {
        this[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = producerConfig.property("bootstrap.servers").getList()
        this[ProducerConfig.CLIENT_ID_CONFIG] = producerConfig.property("client.id").getString()
        this[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = producerConfig.property("key.serializer").getString()
        this[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = producerConfig.property("value.serializer").getString()
    }

    return KafkaProducer(producerProps)
}

fun Application.module() {
    configureDatabases()

    val eventProducer = buildProducer<Long, String>(environment)

    val eventService = EventService(eventProducer)

    configureRouting(eventService)
}
