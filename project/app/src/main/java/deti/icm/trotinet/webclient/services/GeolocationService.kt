package deti.icm.trotinet.webclient.services

import deti.icm.trotinet.webclient.model.Geolocation
import retrofit2.Call
import retrofit2.http.*

interface GeolocationService {

    @GET("city?format=json&key=QSG360BXRHRGRVATXGFBQVUQN2GK2VWL")
    fun getGeodata(
        @Query("lat") lat: String,
        @Query("lng") lng: String
    ): Call<Geolocation>

}