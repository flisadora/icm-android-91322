package deti.icm.trotinet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val uid: Long = 0L,
    val name: String,
    val email: String,
    val balance: Double,
    val distance: Double,
)