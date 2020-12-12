package com.yennyh.capripicnic.shared.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.ListProductItemBinding
import com.yennyh.capripicnic.models.Product



class TypeProductsRVAdapter(
    var productsList: ArrayList<Product>,
    private val onItemClickListener: OnItemClickListener
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
        private val binding = ListProductItemBinding.bind(itemView)

        fun bindThematic(product: Product) {
            Picasso.get().load(product.photos[0].url).into(binding.productslistImageView)
            binding.titleTextView.text = product.description
            binding.itemCardView.setOnClickListener {
                onItemClickListener.onItemClick(product)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(products: Product)
    }

}