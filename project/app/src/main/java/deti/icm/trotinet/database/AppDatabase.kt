package deti.icm.trotinet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import deti.icm.trotinet.database.converter.Converters
import deti.icm.trotinet.database.dao.AppDao
import deti.icm.trotinet.model.Ride
import deti.icm.trotinet.model.User

@Database(entities = [User::class, Ride::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        fun instance(context: Context) : AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "trotinet.db"
            ).allowMainThreadQueries()
                .build()
        }
    }

}