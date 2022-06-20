package deti.icm.trotinet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Scooter (
    @PrimaryKey(autoGenerate = true)
    val sid: Long = 0L,
    val available: Boolean,
    val batteryLevel: Int,
    val latitude: Double,
    val longitude: Double
)