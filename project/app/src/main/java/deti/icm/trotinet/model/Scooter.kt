package deti.icm.trotinet.model

class Scooter (
    val id: String? = null,
    val available: Boolean = true,
    val batteryLevel: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    fun toScooter(id: String): Scooter = Scooter(
        id = id,
        available = available,
        batteryLevel = batteryLevel,
        latitude = latitude,
        longitude = longitude
    )
}