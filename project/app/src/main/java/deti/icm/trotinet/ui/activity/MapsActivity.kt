package deti.icm.trotinet.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import deti.icm.trotinet.R
import deti.icm.trotinet.databinding.ActivityMapsBinding
import deti.icm.trotinet.webclient.RetrofitInitializer
import deti.icm.trotinet.webclient.model.ForecastCall
import deti.icm.trotinet.webclient.model.ForecastResponse
import deti.icm.trotinet.webclient.model.Geolocation
import deti.icm.trotinet.webclient.model.NearbySearch
import deti.icm.trotinet.webclient.utility.ConstantsNearbySearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val requestCodeLocationPermission = 1
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location = LatLng(40.6434, -8.6406)
    private lateinit var city: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listenButtons()

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
        getDeviceLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
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
                Log.i("###ISADORA", "LAST: lat: ${last.latitude}, lon: ${last.longitude}")
                mMap.addMarker(MarkerOptions().position(location))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
                mMap.setMinZoomPreference(15.0F)
            }
            .addOnFailureListener {
                Toast.makeText(
                    this, "Failed on getting current location",
                    Toast.LENGTH_LONG
                ).show()
            }
        Log.i("###ISADORA", "LOCAL: lat: ${location.latitude}, lon: ${location.longitude}")
        callNearbySearch()
        //Timer().schedule(2000) { getLocation() }
        getLocation()
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


    private fun listenButtons() {
        val buttonUserDetails = findViewById<FloatingActionButton>(R.id.user_details_button)
        val buttonListRides = findViewById<FloatingActionButton>(R.id.list_rides_button)

        buttonUserDetails.setOnClickListener {
            val intent = Intent(this, UserDetails::class.java)
            startActivity(intent)
        }

        buttonListRides.setOnClickListener {
            val intent = Intent(this, ListRides::class.java)
            startActivity(intent)
        }
    }


    private fun callNearbySearch() {
        RetrofitInitializer().nearbySearchService
        .getNearbySearch(("${location.latitude},${location.longitude}"),
                        ConstantsNearbySearch.TYPE,
                        ConstantsNearbySearch.RADIUS,
                        ConstantsNearbySearch.API_KEY
        ).enqueue(
            object : Callback<NearbySearch> {
                override fun onResponse(
                    call: Call<NearbySearch>,
                    response: Response<NearbySearch>
                ) {
                    val nearbySearch = response.body()
                    Log.i("###ISADORA", "NEARBY: ${response.code()}")
                    if (nearbySearch?.status.equals("OK")) {
                        val places = ArrayList<LatLng>()

                        for (item in nearbySearch?.results!!) {
                            val place = LatLng(item.geometry.location?.lat, item.geometry.location?.lng)
                            places.add(place)
                            mMap.addMarker(MarkerOptions().position(location)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                        }

                    }
                }

                override fun onFailure(call: Call<NearbySearch>, t: Throwable) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG)
                }

            }
        )
    }


    private fun getLocation() {
        RetrofitInitializer().geolocationService
        .getGeodata(location.latitude.toString(), location.longitude.toString()).enqueue(
            object : Callback<Geolocation> {
                override fun onFailure(call: Call<Geolocation>, t: Throwable) {
                    Log.i("###ISADORA", "geolocation api call failure")
                }

                override fun onResponse(call: Call<Geolocation>, response: Response<Geolocation>) {
                    val geolocation = response.body()
                    city = geolocation?.region.toString()
                    Log.i("###ISADORA", "CITY: $city")
                    getForecast(city)
                }
            }
        )
    }


    private fun getForecast(city: String) {
        val forecastCallData = ForecastCall(city)
        RetrofitInitializer().weatherService.getWeather(forecastCallData).enqueue(
            object : Callback<ForecastResponse> {

                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                    Log.i("###ISADORA", "weather api call failure")
                }

                override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                    val forecast = response.body()
                    val toast: Toast = Toast.makeText(
                        applicationContext,
                        "Weather for ${forecastCallData.location}: ${forecast?.condition}, temperature ${forecast?.temp_c}ºC",
                        Toast.LENGTH_LONG
                    )
                    toast.setGravity(Gravity.CENTER, 0,0)
                    toast.show()
                    Log.i("###ISADORA", "${forecast?.condition}, temperature ${forecast?.temp_c}C")

                    setWeatherIcon(forecast?.icon_url)

                }
            }
        )
    }

    private fun setWeatherIcon(iconUrl: String?) {
        val weatherIcon = findViewById<ImageView>(R.id.weather_icon)
        var iconUri = ""
        iconUrl?.let { iconUri = "http:"+it }

        // Documentation: https://github.com/bumptech/glide
        Glide.with(this).load(iconUri).into(weatherIcon)
    }
}