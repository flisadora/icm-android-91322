package deti.icm.trotinet.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserRides(
    @Embedded val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "eid"
    )
    val rides: List<Ride>
)
