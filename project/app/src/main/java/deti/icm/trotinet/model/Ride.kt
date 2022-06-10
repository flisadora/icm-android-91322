package deti.icm.trotinet.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

//one-to-many relationship between User and Ride

@Entity
data class Ride(
    @PrimaryKey(autoGenerate = true)
    val eid: Long = 0L,
    val userId: Long,
    val distance: Double,
    val cost: Double,
    val startRoute: String,
    val endRoute: String,
    val date: LocalDateTime?,
)