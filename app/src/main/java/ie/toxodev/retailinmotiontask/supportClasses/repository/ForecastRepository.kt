package ie.toxodev.retailinmotiontask.supportClasses.repository

import androidx.lifecycle.MutableLiveData
import ie.toxodev.retailinmotiontask.supportClasses.forecastAPI.ForecastAPI
import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.ForecastResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

class ForecastRepository @Inject constructor(private val forecastAPI: ForecastAPI) {

    val lvdForecastResult: MutableLiveData<Result<ForecastResponse>> = MutableLiveData()

    lateinit var coroutineScope: CoroutineScope

    fun initialize(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.coroutineScope = scope.plus(dispatcher)
    }

    fun retrieveForecast(station: String) {
        this.coroutineScope.launch {
            forecastAPI.fetchForecast(station).collect {
                Thread.sleep(250)
                lvdForecastResult.postValue(it)
            }
        }
    }
}
