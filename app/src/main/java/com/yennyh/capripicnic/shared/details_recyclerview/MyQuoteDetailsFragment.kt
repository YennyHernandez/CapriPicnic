package com.yennyh.capripicnic.shared.details_recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentMyQuoteDetailsBinding

class MyQuoteDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMyQuoteDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_quote_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMyQuoteDetailsBinding.bind(view)

        val args: MyQuoteDetailsFragmentArgs by navArgs()
        val myPurchaseViewDetails = args.myQuoteSelect
//        Picasso.get().load(myPurchaseViewDetails.photos[0].url).into(binding.myPurchaseViewDetails)
//        binding.myPurchaseViewDetails.text = picnicViewDetails.description
//        binding.picnicViewNameTextView.text = picnicViewDetails.name
//
    }


}