package example.com.plugins

import example.com.domains.Event
import example.com.services.EventService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.Instant

fun Application.configureRouting(eventService: EventService) {
    routing {
        get("/") {
            val event = Event(1, Instant.now(), "Hello World!")
            eventService.sendEvent(event)

            call.respondText("Hello World!")
        }
    }
}
