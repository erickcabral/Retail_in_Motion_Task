package ie.toxodev.retailinmotiontask.supportClasses.forecastAPI

import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

//    @Headers(
//        "content-encoding:gzip"
//    )
    @GET("xml/get.ashx")
    suspend fun fetchStationForecast(
        @Query("stop") stop: String,
        @Query("action") action: String = "forecast",
        @Query("encrypt") encrypt: Boolean = false
    ): Response<ForecastResponse>
}