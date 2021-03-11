package ie.toxodev.retailinmotiontask.supportClasses.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.retailinmotiontask.R
import ie.toxodev.retailinmotiontask.databinding.ContainerTramInfoBinding
import ie.toxodev.retailinmotiontask.supportClasses.forecastResponse.Tram

class AdapterTram(private val tramList: List<Tram>) :
    RecyclerView.Adapter<AdapterTram.ViewHolderTramInfo>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTramInfo {
        LayoutInflater.from(parent.context).run {
            val binder: ContainerTramInfoBinding =
                DataBindingUtil.inflate(this, R.layout.container_tram_info, parent, false)
            return ViewHolderTramInfo(binder)
        }
    }

    override fun onBindViewHolder(holder: ViewHolderTramInfo, position: Int) {
        val model = tramList[position]
        holder.item.model = model
    }

    override fun getItemCount() = tramList.size

    class ViewHolderTramInfo(val item: ContainerTramInfoBinding) :
        RecyclerView.ViewHolder(item.root)
}