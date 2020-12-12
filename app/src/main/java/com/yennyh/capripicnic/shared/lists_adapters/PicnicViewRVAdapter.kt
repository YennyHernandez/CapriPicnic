package com.yennyh.capripicnic.shared.lists_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.ListPicnicItemBinding
import com.yennyh.capripicnic.models.PicnicView


class PicnicViewRVAdapter(
    var thematicsList: ArrayList<PicnicView>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PicnicViewRVAdapter.ListPicnicViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListPicnicViewHolder {//inflar item
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_picnic_item, parent, false)
        return ListPicnicViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(
        holder: ListPicnicViewHolder,
        position: Int
    ) {  //datos que voy a mostrar de la lista dependiendo de la posicion
        val service = thematicsList[position]
        holder.bindThematic(service)

    }

    override fun getItemCount(): Int { //retorna el tamaño de la lista n veces
        return thematicsList.size
    }


    class ListPicnicViewHolder(
        itemView: View, var onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(itemView) { //coloca los datos en item, setea la informacion de las cajas
        private val binding = ListPicnicItemBinding.bind(itemView)

        fun bindThematic(picnicView: PicnicView) {
            Picasso.get().load(picnicView.photos[0].url).into(binding.picnicImageView)
            binding.titlePicnicTextView.text = picnicView.description
            binding.picnicCardView.setOnClickListener {
                onItemClickListener.onItemClick(picnicView)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(thematic: PicnicView)
    }

}