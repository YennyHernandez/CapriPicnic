package com.yennyh.capripicnic.shared.details_recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentPicnicViewDetailsBinding

class PicnicViewDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPicnicViewDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picnic_view_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPicnicViewDetailsBinding.bind(view)

        val args: PicnicViewDetailsFragmentArgs by navArgs()
        val picnicViewDetails = args.picnicSelect
        Picasso.get().load(picnicViewDetails.photos[0].url).into(binding.picnicViewImageView)
        binding.picnicViewDescriptionTextView.text = picnicViewDetails.description
        binding.picnicViewNameTextView.text = picnicViewDetails.name

    }


}