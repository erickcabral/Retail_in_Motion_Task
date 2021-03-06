package ie.toxodev.retailinmotiontask

import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.Direction
import ie.toxodev.retailinmotiontask.supportClasses.repository.ForecastRepository
import ie.toxodev.retailinmotiontask.testUtils.BaseJunitTest
import ie.toxodev.retailinmotiontask.views.ViewModelForecast
import junit.framework.Assert.assertEquals
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
    fun update_forecast_info() {
        viewModel.getForecastResponse()
        verify(mckdForecastRepository, times(1)).lvdForecastResult
    }

    @Test
    fun return_formatted_forecast_created_time_to_new_pattern() {
        val created = "2021-03-13T17:31:24"
        val formattedTime = "17:31 Mar, 13 2021"
        this.viewModel.getFormattedTime(created).run {
            assertEquals(this, formattedTime)
        }
    }

    // ================== DIRECTION TEST =================== //
    private val directionList = listOf(Direction("Inbound"), Direction("Outbound"))
    @Test
    fun return_inbound_index_if_stillorgan(){
        this.viewModel.getDirectionIndex(ViewModelForecast.STILLORGAN, directionList).run {
            assertEquals(0, this)
        }
    }

    @Test
    fun return_outbound_index_if_marlborough(){
        this.viewModel.getDirectionIndex(ViewModelForecast.MARLBOROUGH, directionList).run {
            assertEquals(1, this)
        }
    }
}