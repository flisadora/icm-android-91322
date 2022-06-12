package deti.icm.trotinet.webclient

import deti.icm.trotinet.webclient.services.GeolocationService
import deti.icm.trotinet.webclient.services.WeatherService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInitializer {

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    // API -> https://m3o.com/weather/api
    private val retrofitForecast: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.m3o.com/v1/weather/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val weatherService = retrofitForecast.create(WeatherService::class.java)


    // API -> https://www.geodatasource.com/web-service/location-search
    private val retrofitGeolocation: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.geodatasource.com/v2/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val geolocationService = retrofitGeolocation.create(GeolocationService::class.java)

}