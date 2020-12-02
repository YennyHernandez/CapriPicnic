package com.yennyh.capripicnic.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentReservationsBinding
import com.yennyh.capripicnic.models.ThematicsPicnics
import com.yennyh.capripicnic.ui.lista.ThematicsRVAdapter

class ReservationsFragment : Fragment(), ThematicsRVAdapter.OnItemClickListener {
    private lateinit var contentRVAdapter: ThematicsRVAdapter
    private lateinit var binding: FragmentReservationsBinding

    var listThematics: MutableList<ThematicsPicnics> =
        mutableListOf()   //usado para bases de datos con firebaserealtime

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentReservationsBinding.bind(view)
        binding.ReservationsRecyclerView.layoutManager =
            LinearLayoutManager(context, VERTICAL, false)
        binding.ReservationsRecyclerView.setHasFixedSize(true)
        contentRVAdapter = ThematicsRVAdapter(
            listThematics as ArrayList<ThematicsPicnics>,
            this@ReservationsFragment
        )
        binding.ReservationsRecyclerView.adapter =
            contentRVAdapter   // hay que cargarle la información
        loadFromFirebase()
        contentRVAdapter.notifyDataSetChanged()
    }

    private fun loadFromFirebase() {
        val database = FirebaseDatabase.getInstance()   //instancia
        val myThematicsRef = database.getReference("thematics_picnics")  //referencia

        listThematics.clear()

        val postListener =
            object : ValueEventListener {  //hace un llamado y devuelve la información que contiene
                override fun onDataChange(snapshot: DataSnapshot) {  //snaps es la data
                    for (data: DataSnapshot in snapshot.children) { // recorrera todos los hijos (items de tabla)
                        val thematicsServer =
                            data.getValue(ThematicsPicnics::class.java)  //lo guarda en una variable
                        thematicsServer?.let { listThematics.add(it) } //lo agrega a la lista
                    }
                    contentRVAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }

        myThematicsRef.addValueEventListener(postListener)  //agrega la información cargada
    }

    override fun onItemClick(thematic: ThematicsPicnics) {  //obtiene la información de lo que se presiono
        // val action = ReservationsFragmentDirections.actionReservaFragmentToDetalleFragment(thematic)
        val action =
            ReservationsFragmentDirections.actionReservationsFragmentToDetalleFragment(thematic)
        findNavController().navigate(action)

    }

}

