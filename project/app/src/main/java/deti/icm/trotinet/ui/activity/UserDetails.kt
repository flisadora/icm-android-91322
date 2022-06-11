package deti.icm.trotinet.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import deti.icm.trotinet.R
import deti.icm.trotinet.database.AppDatabase
import deti.icm.trotinet.webclient.model.ForecastCall
import deti.icm.trotinet.model.User
import deti.icm.trotinet.webclient.RetrofitInitializer
import deti.icm.trotinet.webclient.model.ForecastResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetails : AppCompatActivity(R.layout.activity_user_details) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

    }


    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instance(this)
        val appDao = db.appDao()
        val user = appDao.getAllUsers()[0]
        val userRides = appDao.loadUserRides(user.uid)
        val distance = userRides.sumOf { it.distance }

        val text_view_balance = findViewById<TextView>(R.id.user_details_balance)
        val text_view_distance = findViewById<TextView>(R.id.user_details_distance)
        val text_view_rides = findViewById<TextView>(R.id.user_details_rides)
        val text_input_name = findViewById<TextView>(R.id.user_details_name)
        val text_input_email = findViewById<TextView>(R.id.user_details_email)
        val button_save = findViewById<Button>(R.id.user_details_save)

        text_view_balance.text = user.balance.toString() + "€"
        text_view_distance.text = (distance/1000).toString() +" km"
        text_view_rides.text = userRides.size.toString() + " rides"
        text_input_name.text = user.name
        text_input_email.text = user.email

        button_save.setOnClickListener{
            val editedUser = User(
                user.uid,
                text_input_name.text.toString(),
                text_input_email.text.toString(),
                user.balance,
            )
            appDao.updateUser(editedUser)
        }

        getForecast()
    }


    fun getForecast() {
        val forecastCallData = ForecastCall("Aveiro")
        RetrofitInitializer().weatherService.getWeather(forecastCallData).enqueue(
            object : Callback<ForecastResponse> {

                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                    Log.i("###ISADORA", "api call failure")
                }

                override fun onResponse(
                    call: Call<ForecastResponse>,
                    response: Response<ForecastResponse>
                ) {
                    val forecast = response.body()
                    Toast.makeText(
                        applicationContext,
                        "Weather for ${forecastCallData.location}: ${forecast?.condition}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i("###ISADORA", "${forecast?.condition}")
                }
            }
        )
    }

}