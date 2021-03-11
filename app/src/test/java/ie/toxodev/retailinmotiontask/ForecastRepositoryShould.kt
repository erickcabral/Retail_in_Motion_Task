package ie.toxodev.retailinmotiontask

import br.toxodev.cinefilo.testUtils.getValueForTest
import com.nhaarman.mockitokotlin2.whenever
import ie.toxodev.retailinmotiontask.supportClasses.forecastAPI.ForecastAPI
import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.ForecastResponse
import ie.toxodev.retailinmotiontask.supportClasses.repository.ForecastRepository
import ie.toxodev.retailinmotiontask.testUtils.BaseJunitTest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.*

class ForecastRepositoryShould : BaseJunitTest() {

    private val repository: ForecastRepository
    private val mckdForecastAPI: ForecastAPI = mock(ForecastAPI::class.java)
    private val scope = mainCoroutineScopeRule
    private val dispatcher = mainCoroutineScopeRule.dispatcher

    init {
        repository = ForecastRepository(mckdForecastAPI)
        this.repository.initialize(scope, dispatcher)
    }

    @Test
    fun repository_scope_not_null() {
        assertNotNull(this.repository.coroutineScope)
    }

    private val forecastResponse = ForecastResponse().apply {
        message = "Green Line is operating normally"
    }

    @Test
    fun retrieve_forecast_and_update_live_data() = runBlockingTest {
        val station = "dummyStation"
        whenever(mckdForecastAPI.fetchForecast(station)).thenReturn(
            flow { emit(Result.success(forecastResponse)) }
        )
        repository.retrieveForecast("dummyStation")
        repository.lvdForecastResult.getValueForTest().run {

            verify(mckdForecastAPI, times(1)).fetchForecast("dummyStation")

            assertNotNull(this)
            assertNotNull(this!!.getOrNull())
            this.getOrNull()!!.run {
                assertEquals(forecastResponse, this)
                assertEquals("Green Line is operating normally", this.message)
            }
        }
    }
}