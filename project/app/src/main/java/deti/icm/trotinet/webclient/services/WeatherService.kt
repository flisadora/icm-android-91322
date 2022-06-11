package deti.icm.trotinet.webclient.services

import deti.icm.trotinet.webclient.model.ForecastCall
import deti.icm.trotinet.webclient.model.ForecastResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface WeatherService {


    @Headers( *[
        //"Content-Type: application/json",
        "Authorization: Bearer MmQ4Nzk1NzEtZjNkZC00ZTY3LWIzN2QtZDdmYWY0NjQzOGRh"
    ])
    @POST("Now")
    fun getWeather(@Body forecastCallData: ForecastCall): Call<ForecastResponse>
}