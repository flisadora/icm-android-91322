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
        val user = users[0]
        //appDao.addRide(Ride(0L, users[0].uid,2800.0, 2.34,"Rua Dr. João de Moura 2, Aveiro; Universidade de Aveiro, 3810-193 Aveiro", LocalDateTime.now()))
        //appDao.addRide(Ride(0L, users[0].uid,1700.0, 1.81,"Universidade de Aveiro, 3810-193 Aveiro; Forum Aveiro, 3810-064 Aveiro", LocalDateTime.now()))
        //appDao.addRide(Ride(0L, users[0].uid,1100.0, 1.53,"Forum Aveiro, 3810-064 Aveiro; Salinas de Aveiro, 3800-180 Aveiro", LocalDateTime.now()))
        val rides = appDao.getAllRides()
        val userRides = appDao.loadUserRides(users[0].uid)
        //val userRides = appDao.getUserRides()
        val text_view_balance = findViewById<TextView>(R.id.user_details_balance)
        val text_view_distance = findViewById<TextView>(R.id.user_details_distance)
        val text_view_rides = findViewById<TextView>(R.id.user_details_rides)
        val text_input_name = findViewById<TextView>(R.id.user_details_name)
        val text_input_email = findViewById<TextView>(R.id.user_details_email)

        text_view_balance.text = user.balance.toString() + " €"
        text_view_distance.text = (user.distance/1000).toString() +" km"
        text_view_rides.text = userRides.size.toString() + " rides"
        text_input_name.text = user.name
        text_input_email.text = user.email
        Log.d("ISADORA", "on create")
        Log.d("ISADORA", "${userRides.size}")
        Log.d("ISADORA", "$userRides")
    }

}