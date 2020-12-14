package com.yennyh.capripicnic.shared.details_recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentProductViewDetailsBinding

class ProductViewDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductViewDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_view_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProductViewDetailsBinding.bind(view)

        val args: ProductViewDetailsFragmentArgs by navArgs()
        val productDetails = args.productSelect

        Picasso.get().load(productDetails.photos[0].url).into(binding.productListImageView)
        binding.descriptionProdutTextView.text = productDetails.description
        binding.titleProductTextView.text = productDetails.name

    }


}