package ie.toxodev.retailinmotiontask.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ie.toxodev.retailinmotiontask.R
import ie.toxodev.retailinmotiontask.databinding.ViewForecastBinding
import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.ForecastResponse
import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.Tram
import java.time.LocalTime

@AndroidEntryPoint
class ViewForecast : Fragment(), View.OnClickListener {
    companion object {
        const val TAG = "<<_TAG_HERE_>>"
    }

    private lateinit var vBinder: ViewForecastBinding //Layout Binder
    private val vModel: ViewModelForecast by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retrieveForecast()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.vBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.view_forecast,
            container, false
        )
        this.vBinder.run {
            this.tramList = emptyList<Tram>()
            this.contStationBinder = vModel.contStationBinder
            this.contAbvBinder = vModel.contStationAbvBinder
            this.contDirectionBinder = vModel.contDirectionBinder
            this.floatBtnUpdate.setOnClickListener(this@ViewForecast)
        }
        this.initializeLiveData()
        return this.vBinder.root
    }

    private fun retrieveForecast() {
        vModel.retrieveForecast(LocalTime.now())
    }

    private fun initializeLiveData() {
        this.vModel.getForecastResponse().observe(viewLifecycleOwner, Observer {
            if (it.isSuccess && it.getOrNull() != null) {
                updateView(it.getOrNull()!!)
            } else {
                TODO("TODO")
            }
        })
    }

    private fun updateView(forecast: ForecastResponse) {
        this.vBinder.run {
            this.lineStatus = forecast.message

            this.tramList = forecast.direction[1].tram

            this.contStationBinder =
                vModel.contStationBinder.apply { this.info = forecast.stop }
            this.contAbvBinder =
                vModel.contStationAbvBinder.apply { this.info = forecast.stopAbv }
            this.contDirectionBinder =
                vModel.contDirectionBinder.apply { this.info = forecast.direction[0].name }
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.floatBtnUpdate -> {
                    this.vBinder.tramList = emptyList<List<Tram>>()
                    this.retrieveForecast()
                }
            }
        }
    }


}