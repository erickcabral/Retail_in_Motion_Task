package ie.toxodev.retailinmotiontask.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.toxodev.retailinmotiontask.supportClasses.binderModels.ContainerTextInfoBinderModel
import ie.toxodev.retailinmotiontask.supportClasses.repository.ForecastRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ViewModelForecast @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    val contStationBinder: ContainerTextInfoBinderModel = ContainerTextInfoBinderModel(
        "Station"
    )

    val contStationAbvBinder: ContainerTextInfoBinderModel =
        ContainerTextInfoBinderModel("Station Abv")

    val contDirectionBinder: ContainerTextInfoBinderModel =
        ContainerTextInfoBinderModel("Direction")

    val contCurrentTime: ContainerTextInfoBinderModel =
        ContainerTextInfoBinderModel("Requested Time")

    companion object {
        const val MARLBOROUGH = "mar"
        const val STILLORGAN = "sti"
    }

    init {
        setupRepositories(viewModelScope, Dispatchers.IO)
    }

    fun setupRepositories(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.repository.initialize(scope, dispatcher)
    }

    fun retrieveForecast(currentTime: LocalTime) {
        val isNoonOrMidnight = currentTime == LocalTime.NOON || currentTime == LocalTime.MIDNIGHT
        val marTimeGap =
            currentTime.isAfter(LocalTime.MIDNIGHT) && currentTime.isBefore(LocalTime.NOON)

        if (marTimeGap || isNoonOrMidnight) {
            this.repository.retrieveForecast(MARLBOROUGH)
        } else {
            this.repository.retrieveForecast(STILLORGAN)
        }
    }

    fun getForecastResponse() = this.repository.lvdForecastResult

    fun getFormattedTime(created: String): String {
        val parse = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(created)
        return LocalDateTime.from(parse).format(DateTimeFormatter.ofPattern("MMM-dd-YYYY HH:mm"))
    }

}
