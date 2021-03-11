package ie.toxodev.retailinmotiontask

import ie.toxodev.retailinmotiontask.supportClasses.repository.ForecastRepository
import ie.toxodev.retailinmotiontask.testUtils.BaseJunitTest
import ie.toxodev.retailinmotiontask.views.ViewModelForecast
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.junit.Test
import org.mockito.Mockito.*
import java.time.LocalTime

class ViewModelForecastShould : BaseJunitTest() {


    private var viewModel: ViewModelForecast

    private val mckdForecastRepository: ForecastRepository = mock(ForecastRepository::class.java)
    private val mckdScope: CoroutineScope = mock(CoroutineScope::class.java)
    private val mckdDispatcher: CoroutineDispatcher = mock(CoroutineDispatcher::class.java)

    init {
        viewModel = ViewModelForecast(mckdForecastRepository)
    }

    @Test
    fun initialize_container_binders() {
        assertNotNull(viewModel.contStationBinder)
        assertNotNull(viewModel.contStationAbvBinder)
        assertNotNull(viewModel.contDirectionBinder)
    }

    @Test
    fun initialize_repositories() {
        this.viewModel.setupRepositories(mckdScope, mckdDispatcher)
        verify(mckdForecastRepository, times(1)).initialize(mckdScope, mckdDispatcher)
    }

    @Test
    fun retrieve_marlborough_forecast_if_time_between_0000_1200() {
        val currentTime = LocalTime.MIDNIGHT
        this.viewModel.retrieveForecast(currentTime)
        verify(mckdForecastRepository, times(1)).retrieveForecast(ViewModelForecast.MARLBOROUGH)
    }

    @Test
    fun retrieve_stillorgan_forecast_if_time_between_0001_1201() {
        val currentTime = LocalTime.of(12, 1)
        this.viewModel.retrieveForecast(currentTime)
        verify(mckdForecastRepository, times(1)).retrieveForecast(ViewModelForecast.STILLORGAN)
    }

    @Test
    fun update_forecast_info(){
        viewModel.getForecastResponse()
        verify(mckdForecastRepository, times(1)).lvdForecastResult
    }
}