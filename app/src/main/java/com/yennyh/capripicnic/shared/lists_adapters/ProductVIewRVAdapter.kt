package com.yennyh.capripicnic.shared.lists_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.ListProductViewItemBinding
import com.yennyh.capripicnic.models.Product


class ProductVIewRVAdapter(
    var productsList: ArrayList<Product>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ProductVIewRVAdapter.ListProductsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListProductsViewHolder {//inflar item
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_product_view_item, parent, false)
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
        private val binding = ListProductViewItemBinding.bind(itemView)

        fun bindThematic(productView: Product) {
            Picasso.get().load(productView.photos[0].url).into(binding.productListImageView)

            binding.titleProductTextView.text = productView.name
            binding.productCardView.setOnClickListener {
                onItemClickListener.onItemClick(productView)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(products: Product)
    }

}