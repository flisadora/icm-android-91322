package deti.icm.trotinet.database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import deti.icm.trotinet.model.Ride
import deti.icm.trotinet.model.Scooter
import deti.icm.trotinet.model.User

class Repository(
    private val firestore: FirebaseFirestore
) {

    fun findUserById(id: String): User? {
        var user: User = User(id)
        firestore.collection("User").document(id).get()
            .addOnSuccessListener { result ->
                result.toObject<User>()?.toUser(result.id)?.let { it ->
                    Log.i("#REPOSITORIO", "IT: ${it.name}")
                    user = it
                    Log.i("#REPOSITORIO", "ITUSER: ${user.name}")
                }
            }
        Log.i("#REPOSITORIO", "USER: $user.name")
        return user
    }

    fun updateUser(user: User) {
        firestore.collection("User").document(user.id.toString()).set(user)
    }

    fun getUserRides(userId: String): List<Ride> {
        val rides: MutableList<Ride> = mutableListOf()
        firestore.collection("Ride").whereEqualTo("userId", userId).get()
            .addOnSuccessListener { it?.let { result ->
                for (document in result) {
                    document.toObject<Ride>().toRide(userId).let { ride: Ride ->
                        rides.add(ride)
                    }
                }
            }
        }
        return rides
    }

    fun getScooters(): List<Scooter> {
        val scooters: MutableList<Scooter> = mutableListOf()
        firestore.collection("Scooter").get()
            .addOnSuccessListener { it?.let { result ->
                for (document in result) {
                    document.toObject<Scooter>().toScooter(document.id).let {
                        scooter: Scooter -> scooters.add(scooter)
                    }
                }
            }
        }
        Log.i("#REPOSITORIO", "$scooters")
        return scooters
    }

    fun insertScooter(scooter: Scooter) {
        val newScooter = hashMapOf(
            "available" to scooter.available,
            "batteryLevel" to scooter.batteryLevel,
            "latitude" to scooter.latitude,
            "longitude" to scooter.longitude
        )
        firestore.collection("Scooter").document().set(newScooter)
    }

    fun deleteScooters(scooters: List<Scooter>) {
        for (scooter in scooters) {
            Log.i("#REPOSITORIO", "DELETE")
            firestore.collection("Scooter").document(scooter.id.toString()).delete()
        }
    }

}