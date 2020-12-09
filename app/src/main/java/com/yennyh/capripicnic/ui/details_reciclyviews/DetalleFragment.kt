package com.yennyh.capripicnic.ui.details_reciclyviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentDetalleBinding

class DetalleFragment : Fragment() {

    private lateinit var binding: FragmentDetalleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetalleBinding.bind(view)

        val args: DetalleFragmentArgs by navArgs()
        val themeDetalle = args.themesSeleccionado
        binding.nombreTextView.text = themeDetalle.description
        binding.nameTextView.text = themeDetalle.name

    }


}