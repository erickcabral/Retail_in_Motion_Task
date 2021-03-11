package ie.toxodev.retailinmotiontask.supportClasses.forecastAPI

import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.ForecastResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForecastAPI @Inject constructor(
    private val api: API
) {
    suspend fun fetchForecast(station: String): Flow<Result<ForecastResponse>> {
        return flow<Result<ForecastResponse>> {
            api.fetchStationForecast(station).run {
                if (this.isSuccessful && this.body() != null) {
                    emit(Result.success(this.body()!!))
                } else {
                    emit(Result.failure(Throwable("Forecast response body returned invalid")))
                }
            }
        }.catch {
            emit(Result.failure(Throwable("API Marlborough Forecast Request error")))
        }
    }
}
