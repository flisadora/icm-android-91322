package deti.icm.trotinet.webclient.services

import com.google.android.gms.maps.model.LatLng
import deti.icm.trotinet.webclient.model.NearbySearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NearbySearchService {

    @GET("json")
    fun getNearbySearch(
        @Query("location") location: String,
        @Query("type") type: String,
        @Query("radius") radius: String,
        @Query("key") key: String
    ) : Call<NearbySearch>
}