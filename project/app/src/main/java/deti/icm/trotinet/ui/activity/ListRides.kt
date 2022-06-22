package deti.icm.trotinet.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import deti.icm.trotinet.R
import deti.icm.trotinet.database.Repository
import deti.icm.trotinet.model.Ride
import deti.icm.trotinet.ui.recyclerview.adapter.ListRidesAdapter

class ListRides() : AppCompatActivity(R.layout.activity_list_rides) {

    private val adapter = ListRidesAdapter(context = this)
    private val repository: Repository = Repository(Firebase.firestore)
    private val userId: String = "wJ3dox6MDHBYoLlEXdqO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_rides)

        val firestore = Firebase.firestore
        firestore.collection("User").get().addOnSuccessListener {
            it?.let { snapshot ->
                Log.i("###ISADORA", "FIREBASE: ${snapshot.documents}")
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val rides: List<Ride> = repository.getUserRides(userId)
        Log.i("###ISADORA", rides.toString())
        val recyclerView = findViewById<RecyclerView>(R.id.activity_list_rides_recyclerView)
        recyclerView.adapter = ListRidesAdapter(this, rides)
        adapter.refresh(rides)
    }

}