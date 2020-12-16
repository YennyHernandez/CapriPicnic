package com.yennyh.capripicnic.shared.details_recyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentMyPurchaseDetailsBinding
import com.yennyh.capripicnic.databinding.FragmentPicnicViewDetailsBinding


class MyPurchaseDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMyPurchaseDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_purchase_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMyPurchaseDetailsBinding.bind(view)

        val args: MyPurchaseDetailsFragmentArgs by navArgs()
        val myPurchaseViewDetails = args.myProductSelect
//        Picasso.get().load(picnicViewDetails.photos[0].url).into(binding.picnicViewImageView)
//        binding.picnicViewDescriptionTextView.text = picnicViewDetails.description
//        binding.picnicViewNameTextView.text = picnicViewDetails.name

    }

}