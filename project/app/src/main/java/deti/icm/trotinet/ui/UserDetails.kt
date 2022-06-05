package deti.icm.trotinet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import deti.icm.trotinet.R
import deti.icm.trotinet.database.AppDatabase
import deti.icm.trotinet.model.Ride
import deti.icm.trotinet.model.User
import java.time.LocalDateTime

class UserDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val db = AppDatabase.instance(this)
        val appDao = db.appDao()
        //appDao.deleteAllUsers()
        //appDao.deleteAllRides()
        //appDao.addUser(User(0L,"john doe", "john@ymail.com", 12.34,2800.0))
        //appDao.addUser(User(0L,"sylvester", "looneytunes@ymail.com", 18.74,0.0))
        val users = appDao.getAllUsers()
        //appDao.addRide(Ride(0L, users[0].uid,2800.0, 2.34,"Rua Dr. João de Moura 2, Aveiro; Universidade de Aveiro, 3810-193 Aveiro", LocalDateTime.now()))
        val rides = appDao.getAllRides()
        //val userRides = appDao.loadUserRides(users[0].uid)
        val userRides = appDao.getUserRides()
        val text_view_name = findViewById<TextView>(R.id.user_details_name)
        text_view_name.text = users[0].name
        Log.d("ISADORA", "on create")
        Log.d("ISADORA", "$userRides")
    }

}