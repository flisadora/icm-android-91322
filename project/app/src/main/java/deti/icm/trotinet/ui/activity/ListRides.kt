package deti.icm.trotinet.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import deti.icm.trotinet.R
import deti.icm.trotinet.database.AppDatabase
import deti.icm.trotinet.ui.recyclerview.adapter.ListRidesAdapter

class ListRides : AppCompatActivity(R.layout.activity_list_rides) {

    private val adapter = ListRidesAdapter(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_rides)
    }


    override fun onResume() {
        super.onResume()
        val repository = AppDatabase.instance(this).appDao()
        val users = repository.getAllUsers()
        val rides = repository.loadUserRides(users[0].uid)
        val recyclerView = findViewById<RecyclerView>(R.id.activity_list_rides_recyclerView)
        recyclerView.adapter = ListRidesAdapter(this, rides)
        adapter.refresh(rides)
    }

}