package deti.icm.trotinet.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithRides(
    @Embedded val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "userId"
    )
    val rides: List<Ride>
)
