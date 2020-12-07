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
import com.yennyh.capripicnic.databinding.FragmentProductsBinding
import com.yennyh.capripicnic.lists.TypeProductsRVAdapter
import com.yennyh.capripicnic.models.TypeProducts

class ProductsFragment : Fragment(), TypeProductsRVAdapter.OnItemClickListener {
    private lateinit var contentRVAdapter: TypeProductsRVAdapter
    private lateinit var binding: FragmentProductsBinding

    var listProducts: MutableList<TypeProducts> =
        mutableListOf()   //usado para bases de datos con firebaserealtime

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProductsBinding.bind(view)
        binding.ProductsRecyclerView.layoutManager =
            LinearLayoutManager(context, VERTICAL, false)
        binding.ProductsRecyclerView.setHasFixedSize(true)
        contentRVAdapter = TypeProductsRVAdapter(
            listProducts as ArrayList<TypeProducts>,
            this@ProductsFragment
        )
        binding.ProductsRecyclerView.adapter =
            contentRVAdapter   // hay que cargarle la información
        loadFromFirebase()
        contentRVAdapter.notifyDataSetChanged()
    }

    private fun loadFromFirebase() {
        val database = FirebaseDatabase.getInstance()   //instancia
        val myProductsRef = database.getReference("products")  //referencia

        listProducts.clear()

        val postListener =
            object : ValueEventListener {  //hace un llamado y devuelve la información que contiene
                override fun onDataChange(snapshot: DataSnapshot) {  //snaps es la data
                    for (data: DataSnapshot in snapshot.children) { // recorrera todos los hijos (items de tabla)
                        val productsServer =
                            data.getValue(TypeProducts::class.java)  //lo guarda en una variable
                        productsServer?.let { listProducts.add(it) } //lo agrega a la lista
                    }
                    contentRVAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }

        myProductsRef.addValueEventListener(postListener)  //agrega la información cargada
    }

    override fun onItemClick(products: TypeProducts) {  //obtiene la información de lo que se presiono
        //val action = ReservationsFragmentDirections.actionReservaFragmentToDetalleFragment(thematic)
        val action =
            ProductsFragmentDirections.actionProductsFragmentToDetalleProductsFragment(products)
        findNavController().navigate(action)

    }

}