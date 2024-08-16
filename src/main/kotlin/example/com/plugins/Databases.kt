package example.com.plugins

import io.github.flaxoos.ktor.server.plugins.kafka.Kafka
import io.github.flaxoos.ktor.server.plugins.kafka.MessageTimestampType
import io.github.flaxoos.ktor.server.plugins.kafka.TopicName
import io.github.flaxoos.ktor.server.plugins.kafka.admin
import io.github.flaxoos.ktor.server.plugins.kafka.common
import io.github.flaxoos.ktor.server.plugins.kafka.producer
import io.github.flaxoos.ktor.server.plugins.kafka.topic
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureDatabases() {
    install(Kafka) {
        schemaRegistryUrl = "http://localhost:8081"

        val reportsTopic = TopicName.named("reports")

        topic(reportsTopic) {
            partitions = 1
            replicas = 1
            configs {
                messageTimestampType = MessageTimestampType.CreateTime
            }
        }
        common { // <-- Define common properties
            bootstrapServers = listOf("localhost:9092")
            retries = 1
            clientId = "app-1"
        }
        admin { } // <-- Creates an admin client
        producer { // <-- Creates a producer
            clientId = "app-1"
        }
    }
}
