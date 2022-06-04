package deti.icm.trotinet.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import deti.icm.trotinet.database.converter.Converters
import deti.icm.trotinet.database.dao.AppDao
import deti.icm.trotinet.model.Ride
import deti.icm.trotinet.model.User

@Database(entities = [User::class, Ride::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): AppDao
}