package com.yennyh.capripicnic.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.ListServicesItemBinding
import com.yennyh.capripicnic.models.TypeProducts


class TypeProductsRVAdapter(
    var productsList: ArrayList<TypeProducts>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TypeProductsRVAdapter.ListProductsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListProductsViewHolder {//inflar item
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_services_item, parent, false)
        return ListProductsViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(
        holder: ListProductsViewHolder,
        position: Int
    ) {  //datos que voy a mostrar de la lista dependiendo de la posicion
        val service = productsList[position]
        holder.bindThematic(service)

    }

    override fun getItemCount(): Int { //retorna el tamaño de la lista n veces
        return productsList.size
    }


    class ListProductsViewHolder(
        itemView: View, var onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(itemView) { //coloca los datos en item, setea la informacion de las cajas
        private val binding = ListServicesItemBinding.bind(itemView)

        fun bindThematic(products: TypeProducts) {
           // Picasso.get().load(products.photos).into(binding.);
            binding.titleTextView.text = products.description
            binding.itemCardView.setOnClickListener {
                onItemClickListener.onItemClick(products)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(products: TypeProducts)
    }

}