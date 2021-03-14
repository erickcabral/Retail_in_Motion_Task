package ie.toxodev.retailinmotiontask.views

import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import ie.toxodev.retailinmotiontask.supportClasses.OutputManager
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
        this.isOnline()
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

    private fun isOnline() {
        requireContext().getSystemService(ConnectivityManager::class.java).run {
            val status: NetworkInfo? = this.activeNetworkInfo
            if (status?.isConnectedOrConnecting == true) {
                retrieveForecast()
            } else {
                OutputManager.displayPositiveOnlyAlert(
                    requireContext(), getString(R.string.alert_no_internet),
                    getString(R.string.warning_no_internet_connection)
                )
            }
        }
    }


    private fun retrieveForecast() {
        vModel.retrieveForecast(LocalTime.now())
    }

    private fun initializeLiveData() {
        this.vModel.getForecastResponse().observe(viewLifecycleOwner, Observer {
            if (it.isSuccess && it.getOrNull() != null) {
                updateView(it.getOrNull()!!)
            } else {
                OutputManager.displayPositiveOnlyAlert(
                    requireContext(), getString(R.string.alert_retrieving_error),
                    getString(R.string.warning_error_while_retrieving_forecast)
                )
            }
        })
    }

    private fun updateView(forecast: ForecastResponse) {
        this.vBinder.run {
            this.lineStatus = forecast.message


            this.contStationBinder =
                vModel.contStationBinder.apply { this.info = forecast.stop }

            this.contAbvBinder =
                vModel.contStationAbvBinder.apply { this.info = forecast.stopAbv }

            this.contCurrentTime =
                vModel.contCurrentTime.apply {
                    this.info = vModel.getFormattedTime(forecast.created)
                }


            vModel.getDirectionIndex(forecast.stopAbv, forecast.direction)?.let {index->
                this.contDirectionBinder =
                    vModel.contDirectionBinder.apply {
                        this.info = forecast.direction[index].name
                    }
                this.tramList = forecast.direction[index].tram
            }
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.floatBtnUpdate -> {
                    this.vBinder.tramList = emptyList<List<Tram>>()
                    this.isOnline()
                }
            }
        }
    }
}