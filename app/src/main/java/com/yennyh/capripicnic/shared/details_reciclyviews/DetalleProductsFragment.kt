package com.yennyh.capripicnic.shared.details_reciclyviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentDetalleProductsBinding

class DetalleProductsFragment : Fragment() {

    private lateinit var binding: FragmentDetalleProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetalleProductsBinding.bind(view)

        val args: DetalleProductsFragmentArgs by navArgs()
        val productDetalle = args.productSeleccionado
        binding.nombreTextView.text = productDetalle.description
        binding.nameTextView.text = productDetalle.name

    }


}