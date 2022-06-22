package deti.icm.trotinet.model

import java.time.LocalDateTime

class Ride(
    val id: String? = null,
    val userId: String = "",
    val distance: Double = 0.0,
    val cost: Double = 0.0,
    val startRoute: String = "",
    val endRoute: String = "",
    val date: LocalDateTime? = LocalDateTime.now(),
) {
    fun toRide(userId: String): Ride = Ride(
        id = id,
        userId = userId,
        distance = distance,
        cost = cost,
        startRoute = startRoute,
        endRoute = endRoute,
        date = date
    )
}