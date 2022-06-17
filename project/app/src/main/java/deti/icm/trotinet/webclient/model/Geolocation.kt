package deti.icm.trotinet.webclient.model

data class Geolocation (
    val country: String?,
    val region: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val currency_code: String?,
    val currency_name: String?,
    val currency_symbol: String?,
    val sunrise: String?,
    val sunset: String?,
    val time_zone: String?,
    val distance_km: Double?    
)