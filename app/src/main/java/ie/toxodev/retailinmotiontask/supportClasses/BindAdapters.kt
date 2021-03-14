package ie.toxodev.retailinmotiontask.supportClasses

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.retailinmotiontask.R
import ie.toxodev.retailinmotiontask.supportClasses.adapters.AdapterTram
import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.Tram

@BindingAdapter("bindAdapter:line_status")
fun formatLineStatus(textView: TextView, text: String?) {
    text?.let { status ->
        val resources = textView.context.resources
        textView.text = status
        textView.setTextColor(
            if (text == resources.getString(R.string.line_status_operative))
                resources.getColor(android.R.color.holo_green_dark, textView.context.theme)
            else
                resources.getColor(android.R.color.holo_red_dark, textView.context.theme)
        )
    }
}

@BindingAdapter("bindAdapter:load_tram_list")
fun loadTramInfo(recyclerView: RecyclerView, list: List<Any>?) {
    list?.let {
        AdapterTram(it as List<Tram>).also { adapter ->
            recyclerView.run {
                this.adapter = adapter
                adapter.notifyDataSetChanged()

            }
        }
    }
}