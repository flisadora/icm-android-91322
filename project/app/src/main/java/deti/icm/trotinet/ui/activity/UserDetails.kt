package deti.icm.trotinet.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import deti.icm.trotinet.R
import deti.icm.trotinet.database.AppDatabase
import deti.icm.trotinet.model.User
import okhttp3.internal.format

class UserDetails : AppCompatActivity(R.layout.activity_user_details) {

    private val repository by lazy {
        AppDatabase.instance(this).appDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
    }

    override fun onResume() {
        super.onResume()
        showUserDetails()
    }

    private fun showUserDetails() {
        val user = repository.getAllUsers()[0]
        val userRides = repository.loadUserRides(user.uid)
        val distance = userRides.sumOf { it.distance }

        val text_view_balance = findViewById<TextView>(R.id.user_details_balance)
        val text_view_distance = findViewById<TextView>(R.id.user_details_distance)
        val text_view_rides = findViewById<TextView>(R.id.user_details_rides)
        val text_input_name = findViewById<TextView>(R.id.user_details_name)
        val text_input_email = findViewById<TextView>(R.id.user_details_email)
        val button_save = findViewById<Button>(R.id.user_details_save)

        text_view_balance.text = format("%.2f", user.balance).toString() + "€"
        text_view_distance.text = (distance / 1000).toString() + " km"
        text_view_rides.text = userRides.size.toString() + " rides"
        text_input_name.text = user.name
        text_input_email.text = user.email

        button_save.setOnClickListener {
            val editedUser = User(
                user.uid,
                text_input_name.text.toString(),
                text_input_email.text.toString(),
                user.balance,
            )
            repository.updateUser(editedUser)
            Snackbar.make(it, "Your new information was saved!", Snackbar.LENGTH_LONG).show()
        }
    }

}