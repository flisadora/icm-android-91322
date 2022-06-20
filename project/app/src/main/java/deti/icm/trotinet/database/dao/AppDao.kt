package deti.icm.trotinet.database.dao

import androidx.room.*
import deti.icm.trotinet.model.Ride
import deti.icm.trotinet.model.Scooter
import deti.icm.trotinet.model.User
import deti.icm.trotinet.model.UserRides

@Dao
interface AppDao {

    // ***** USER Database *****
    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM User WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): User

    @Insert
    fun addUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM User")
    fun deleteAllUsers()

    @Update
    fun updateUser(user: User)

    // ***** RIDE Database *****
    @Query("SELECT * FROM Ride ORDER BY Ride.date DESC")
    fun getAllRides(): List<Ride>

    @Insert
    fun addRide(ride: Ride)

    @Delete
    fun deleteRide(ride: Ride)

    @Query("DELETE FROM Ride")
    fun deleteAllRides()

    // Multimap Relationship
    @Query(
        "SELECT * FROM Ride JOIN User ON User.uid = Ride.userId " +
        "WHERE User.uid LIKE :userId ORDER BY Ride.date DESC"
    )
    fun loadUserRides(userId: Long): List<Ride>

    @Transaction
    @Query("SELECT * FROM User")
    fun getUserRides(): List<UserRides>

    // ***** SCOOTER Database *****
    @Query("SELECT * FROM Scooter ORDER BY Scooter.batteryLevel DESC")
    fun getAllScooters(): List<Scooter>

    @Insert
    fun addScooter(scooter: Scooter)

    @Query("DELETE FROM Scooter")
    fun deleteAllScooters()
}
