package ie.toxodev.retailinmotiontask

import com.nhaarman.mockitokotlin2.whenever
import ie.toxodev.retailinmotiontask.supportClasses.forecastAPI.API
import ie.toxodev.retailinmotiontask.supportClasses.forecastAPI.ForecastAPI
import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.ForecastResponse
import ie.toxodev.retailinmotiontask.testUtils.BaseJunitTest
import ie.toxodev.retailinmotiontask.views.ViewModelForecast
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

class ForecastApiShould : BaseJunitTest() {

    private val api: ForecastAPI
    private val mckAPI = mock(API::class.java)

    init {
        api = ForecastAPI(mckAPI)
    }


    @Test
    fun fetch_forecast_from_database() = runBlockingTest {
        val marResponse = ForecastResponse().apply {
            this.message = "Dummy message"
        }
        whenever(mckAPI.fetchStationForecast(ViewModelForecast.MARLBOROUGH)).thenReturn(
            Response.success(marResponse)
        )

        api.fetchForecast(ViewModelForecast.MARLBOROUGH).collect {
            verify(mckAPI, times(1)).fetchStationForecast(ViewModelForecast.MARLBOROUGH)
            assertNotNull(it.getOrNull())
            it.getOrNull()!!.run {
                assertEquals("Dummy message", this.message)
            }
        }
    }
}