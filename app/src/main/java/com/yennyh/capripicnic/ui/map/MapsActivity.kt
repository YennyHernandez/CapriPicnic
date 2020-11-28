package com.yennyh.capripicnic.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.yennyh.capripicnic.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment =
            supportFragmentManager   //pone dentro de la actividad un fragmento de mapa
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)  //se sincroniza con la llave
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap  //pone el mapa al objeto
        setUpMap()
        mMap.setOnPoiClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true //zoom
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL  //forma de visualizar
        val capripicniclt = LatLng(1.8575204, -76.0854647)
        mMap.addMarker(
            MarkerOptions().position(capripicniclt).title("CapriPicinic").snippet("cabras y picnic")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(capripicniclt, 15f))
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        mMap.isMyLocationEnabled = true
    }

    override fun onPoiClick(poi: PointOfInterest?) {
        Toast.makeText(
            this,
            "nombre: ${poi?.name}, latitud: ${poi?.latLng?.latitude},longitud: ${poi?.latLng?.longitude}",
            Toast.LENGTH_SHORT
        ).show()
    }
}