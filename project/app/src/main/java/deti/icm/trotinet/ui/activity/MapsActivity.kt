package deti.icm.trotinet.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import deti.icm.trotinet.R
import deti.icm.trotinet.databinding.ActivityMapsBinding
import deti.icm.trotinet.webclient.RetrofitInitializer
import deti.icm.trotinet.webclient.model.ForecastCall
import deti.icm.trotinet.webclient.model.ForecastResponse
import deti.icm.trotinet.webclient.model.Geolocation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val requestCodeLocationPermission = 1
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location = LatLng(40.6434, -8.6406)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize the FusedLocationClient.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MapsActivity)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    override fun onResume() {
        super.onResume()
        hideStatusBar()
        getLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                this@MapsActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestCodeLocationPermission
            )

            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { last ->
                location = LatLng(last.latitude, last.longitude)
                Log.i("ISADORA", "lat: ${last.latitude}, lon: ${last.longitude}")
                mMap.addMarker(MarkerOptions().position(location))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
                mMap.setMinZoomPreference(16.5F)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed on getting current location",
                    Toast.LENGTH_LONG).show()
            }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeLocationPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("ISADORA", "Permission Granted")
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun hideStatusBar() {
        // Hide the status bar.
        supportActionBar?.hide()
    }


    fun getLocation() {
        var city: String
        RetrofitInitializer().geolocationService
        .getGeodata(location.latitude.toString(), location.longitude.toString()).enqueue(
            object : Callback<Geolocation> {
                override fun onFailure(call: Call<Geolocation>, t: Throwable) {
                    Log.i("###ISADORA", "geolocation api call failure")
                }

                override fun onResponse(call: Call<Geolocation>, response: Response<Geolocation>) {
                    val geolocation = response.body()
                    city = geolocation?.city.toString()
                    getForecast(city)
                }
            }
        )
    }


    fun getForecast(city: String) {
        val forecastCallData = ForecastCall(city)
        RetrofitInitializer().weatherService.getWeather(forecastCallData).enqueue(
            object : Callback<ForecastResponse> {

                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                    Log.i("###ISADORA", "weather api call failure")
                }

                override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                    val forecast = response.body()
                    Toast.makeText(
                        applicationContext,
                        "Weather for ${forecastCallData.location}: ${forecast?.condition}, temperature ${forecast?.temp_c}ºC",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.i("###ISADORA", "${forecast?.condition}, temperature ${forecast?.temp_c}C")
                }
            }
        )
    }
}