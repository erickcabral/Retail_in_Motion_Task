package ie.toxodev.retailinmotiontask.supportClasses

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ie.toxodev.retailinmotiontask.R

@BindingAdapter("bindAdapter:lineStatus")
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