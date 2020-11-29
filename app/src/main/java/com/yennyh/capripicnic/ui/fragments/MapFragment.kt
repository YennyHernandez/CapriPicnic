package com.yennyh.capripicnic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : SupportMapFragment(), OnMapReadyCallback {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        getMapAsync(this)
        return rootView
    }

    override fun onMapReady(map: GoogleMap) {
        map.mapType = GoogleMap.MAP_TYPE_HYBRID

        val capriPicnicLt = LatLng(1.8575204, -76.0854647)
        map.addMarker(
            MarkerOptions().position(capriPicnicLt).title("CapriPicnic").snippet("Cabras y picnic")
        )
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(capriPicnicLt, 15f),
            4000,
            null
        )

    }

}