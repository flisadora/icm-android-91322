package deti.icm.trotinet.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import deti.icm.trotinet.R
import deti.icm.trotinet.database.Repository
import deti.icm.trotinet.model.User
import okhttp3.internal.format

class UserDetails : AppCompatActivity(R.layout.activity_user_details) {

    private val repository: Repository = Repository(Firebase.firestore)
    private val userId: String = "wJ3dox6MDHBYoLlEXdqO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        //repository.addUser(User(0L, "Doug", "dougfanny@email.com", 14.7))
    }


    override fun onResume() {
        super.onResume()
        showUserDetails()
    }

    private fun showUserDetails() {
        val user = repository.findUserById(userId)
        //repository.addRide(Ride(0L, user.uid, 2800.0, 3.25,"Estação de Aveiro", "Universidade de Aveiro, 3810-193 Aveiro", LocalDateTime.now()))
        //repository.addRide(Ride(0L, user.uid, 2100.0, 2.5,"Universidade de Aveiro, 3810-193 Aveiro", "Fórum de Aveiro, 3810-193 Aveiro", LocalDateTime.now()))
        val userRides = repository.getUserRides(userId)
        val distance = userRides.sumOf { it.distance }

        val text_view_balance = findViewById<TextView>(R.id.user_details_balance)
        val text_view_distance = findViewById<TextView>(R.id.user_details_distance)
        val text_view_rides = findViewById<TextView>(R.id.user_details_rides)
        val text_input_name = findViewById<TextView>(R.id.user_details_name)
        val text_input_email = findViewById<TextView>(R.id.user_details_email)
        val button_save = findViewById<Button>(R.id.user_details_save)

        text_view_balance.text = format("%.2f", user!!.balance).toString() + "€"
        text_view_distance.text = (distance / 1000).toString() + " km"
        text_view_rides.text = userRides.size.toString() + " rides"
        text_input_name.text = user?.name
        text_input_email.text = user?.email

        button_save.setOnClickListener {
            val editedUser = User(
                user?.id,
                text_input_name.text.toString(),
                text_input_email.text.toString(),
                user!!.balance,
            )
            repository.updateUser(editedUser)
            Snackbar.make(it, "Your new information was saved!", Snackbar.LENGTH_LONG).show()
        }
    }

}