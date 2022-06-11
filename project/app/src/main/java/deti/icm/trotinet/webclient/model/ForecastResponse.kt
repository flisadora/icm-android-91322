package deti.icm.trotinet.webclient.model

data class ForecastResponse (
    val location: String?,
    val region: String?,
    val country: String?,
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    val local_time: String?,
    val temp_c: Double?,
    val temp_f: Double?,
    val feels_like_c: Double?,
    val feels_like_f: Double?,
    val humidity: Double?,
    val cloud: Double?,
    val daytime: Boolean?,
    val condition: String?,
    val icon_url: String?,
    val wind_mph: Double?,
    val wind_kph: Double?,
    val wind_direction: String?,
    val wind_degree: Double?
)