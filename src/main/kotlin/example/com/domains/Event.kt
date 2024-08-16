package example.com.domains

import java.time.Instant

data class Event(val id: Long, val timestamp: Instant, val message: String) {
    companion object {
        fun fromString(string: String): Event {
            val parts = string.split(",")
            return Event(parts[0].toLong(), Instant.parse(parts[1]), parts[2])
        }
    }

    override fun toString(): String {
        return "$id,$timestamp,$message"
    }
}
