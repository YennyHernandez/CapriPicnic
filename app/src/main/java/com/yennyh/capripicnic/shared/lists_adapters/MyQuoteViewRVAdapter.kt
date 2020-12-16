package com.yennyh.capripicnic.shared.lists_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.ListMyProductsItemBinding
import com.yennyh.capripicnic.models.Product

class MyQuoteViewRVAdapter(
    var myQuotesList: ArrayList<Product>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MyQuoteViewRVAdapter.ListMyQuotesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMyQuotesViewHolder {//inflar item


        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_my_products_item, parent, false)
        return ListMyQuotesViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(
        holder: ListMyQuotesViewHolder,
        position: Int
    ) {  //datos que voy a mostrar de la lista dependiendo de la posicion
        val service = myQuotesList[position]
        holder.bindMyQuotes(service)

    }

    override fun getItemCount(): Int { //retorna el tamaño de la lista n veces
        return myQuotesList.size
    }


    class ListMyQuotesViewHolder(
        itemView: View, var onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(itemView) { //coloca los datos en item, setea la informacion de las cajas
        private val binding = ListMyProductsItemBinding.bind(itemView)

        fun bindMyQuotes(myQuoteView: Product) {
            Picasso.get().load(myQuoteView.photos[0].url).into(binding.listMyProductsImageView)

            binding.nameMyProductListTextView.text = myQuoteView.name
            binding.listMyProductsCardView.setOnClickListener {
                onItemClickListener.onItemClick(myQuoteView)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(products: Product)
    }

}