package deti.icm.trotinet.database.dao

import androidx.room.*
import deti.icm.trotinet.model.User
import deti.icm.trotinet.model.UserWithRides

@Dao
interface AppDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): User

    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersWithRides(): List<UserWithRides>

    @Insert
    fun add(user: User)

    @Delete
    fun delete(user: User)
}
