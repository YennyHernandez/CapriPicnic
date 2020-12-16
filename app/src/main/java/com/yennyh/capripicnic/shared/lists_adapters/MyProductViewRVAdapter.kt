package com.yennyh.capripicnic.shared.lists_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.ListMyProductsItemBinding
import com.yennyh.capripicnic.models.Product

class MyProductRVAdapter(
    var myProductsList: ArrayList<Product>,
    private val onItemClickListener: OnItemClickListener
) :
        RecyclerView.Adapter<MyProductRVAdapter.ListMyProductsViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ListMyProductsViewHolder {//inflar item


        val itemView =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_my_products_item, parent, false)
        return ListMyProductsViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(
            holder: ListMyProductsViewHolder,
            position: Int
    ) {  //datos que voy a mostrar de la lista dependiendo de la posicion
        val service = myProductsList[position]
        holder.bindThematic(service)

    }

    override fun getItemCount(): Int { //retorna el tamaño de la lista n veces
        return myProductsList.size
    }


    class ListMyProductsViewHolder(
            itemView: View, var onItemClickListener: OnItemClickListener
    ) :
            RecyclerView.ViewHolder(itemView) { //coloca los datos en item, setea la informacion de las cajas
        private val binding = ListMyProductsItemBinding.bind(itemView)

        fun bindThematic(myProductView: Product) {
            Picasso.get().load(myProductView.photos[0].url).into(binding.listMyProductsImageView)

            binding.nameMyProductListTextView.text = myProductView.name
            binding.listMyProductsCardView.setOnClickListener {
                onItemClickListener.onItemClick(myProductView)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(products: Product)
    }

}