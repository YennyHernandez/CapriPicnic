package com.yennyh.capripicnic.ui.lista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.ListServicesItemBinding
import com.yennyh.capripicnic.models.ThematicsPicnics


class ThematicsRVAdapter(var thematicsList: ArrayList<ThematicsPicnics>) :
    RecyclerView.Adapter<ThematicsRVAdapter.ListThematicsViewHolder>() {
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListThematicsViewHolder {//inflar item
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_services_item, parent, false)
        return ListThematicsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ListThematicsViewHolder,
        position: Int
    ) {  //datos que voy a mostrar de la lista dependiendo de la posicion
        val service = thematicsList[position]
        holder.bindThematic(service)

    }

    override fun getItemCount(): Int { //retorna el tamaño de la lista n veces
        return thematicsList.size
    }

    class ListThematicsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) { //coloca los datos en item, setea la informacion de las cajas
        private val binding = ListServicesItemBinding.bind(itemView)

        fun bindThematic(thematic: ThematicsPicnics) {
            binding.titleTextView.text = thematic.description
        }
    }

}