package com.yennyh.capripicnic.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.ListServicesItemBinding
import com.yennyh.capripicnic.models.ThematicsPicnics


class ThematicsRVAdapter(
    var thematicsList: ArrayList<ThematicsPicnics>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ThematicsRVAdapter.ListThematicsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListThematicsViewHolder {//inflar item
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_services_item, parent, false)
        return ListThematicsViewHolder(itemView, onItemClickListener)
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


    class ListThematicsViewHolder(
        itemView: View, var onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(itemView) { //coloca los datos en item, setea la informacion de las cajas
        private val binding = ListServicesItemBinding.bind(itemView)

        fun bindThematic(thematic: ThematicsPicnics) {
            Picasso.get().load(thematic.photos).into(binding.reservaImageView)
            binding.titleTextView.text = thematic.description
            binding.itemCardView.setOnClickListener {
                onItemClickListener.onItemClick(thematic)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(thematic: ThematicsPicnics)
    }

}