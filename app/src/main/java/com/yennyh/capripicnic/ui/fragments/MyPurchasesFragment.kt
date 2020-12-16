package com.yennyh.capripicnic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentMyPurchasesBinding
import com.yennyh.capripicnic.models.*
import com.yennyh.capripicnic.services.AuthService
import com.yennyh.capripicnic.shared.lists_adapters.MyProductRVAdapter

class MyPurchasesFragment : Fragment(), MyProductRVAdapter.OnItemClickListener {
    private lateinit var contentRVAdapter: MyProductRVAdapter
    private lateinit var binding: FragmentMyPurchasesBinding
    private lateinit var authSvc : AuthService

    var listMyProducts: MutableList<Product> =
            mutableListOf()   //usado para bases de datos con firebaserealtime

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_purchases, container, false) //purchase contiene picnics y productos comprados
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        authSvc = AuthService()
        binding = FragmentMyPurchasesBinding.bind(view)
        binding.myPurchaseRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.myPurchaseRecyclerView.setHasFixedSize(true)
        contentRVAdapter = MyProductRVAdapter(
                listMyProducts as ArrayList<Product>,
                this@MyPurchasesFragment
        )
        binding.myPurchaseRecyclerView.adapter =
                contentRVAdapter   // hay que cargarle la información
        loadFromFirebase()
        contentRVAdapter.notifyDataSetChanged()
    }

    private fun loadFromFirebase() {
        listMyProducts.clear()
        val currentUserUid = authSvc.getCurrentUser().uid
        val database = FirebaseDatabase.getInstance()   //instancia
        val myCurrentUserRef= database.getReference("users").child(currentUserUid)  //referencia

        val postListenerUserDB =
                object : ValueEventListener {  //hace un llamado y devuelve la información que contiene el usuario
                    override fun onDataChange(dataSnapshotUser: DataSnapshot) {  //snaps es la data del usuario
                        val user: User = dataSnapshotUser.getValue(User::class.java)!!
                        for(cartId: IdCart in user.idCarts!!){
                            val myCartRef= database.getReference("carts").child(cartId.id)

                            val postListenerCartDB =
                                object : ValueEventListener{
                                    override fun onDataChange(dataSnapshotCart: DataSnapshot) {
                                        val cart: Cart = dataSnapshotCart.getValue(Cart::class.java)!!
                                        for(productsId: IdProduct in cart.idProducts){
                                            val myProductRef = database.getReference("products").child(productsId.id)

                                            val postListenerProductDB =
                                                    object : ValueEventListener{
                                                        override fun onDataChange(dataSnapshotProduct: DataSnapshot) {
                                                            val product = dataSnapshotCart.getValue(Product ::class.java)
                                                            product?.let { listMyProducts.add(it) }
                                                        }
                                                        override fun onCancelled(error: DatabaseError) {
                                                            onShowErrorDataChange(error)
                                                        }
                                                    }
                                            myProductRef.addValueEventListener(postListenerProductDB)
                                        }
                                        contentRVAdapter.notifyDataSetChanged()
                                    }
                                    override fun onCancelled(error: DatabaseError) {
                                        onShowErrorDataChange(error)
                                    }
                                }
                            myCartRef.addValueEventListener(postListenerCartDB)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        onShowErrorDataChange(error)
                    }
                }
        myCurrentUserRef.addValueEventListener(postListenerUserDB)  //agrega la información cargada
    }

    override fun onItemClick(product: Product) {  //obtiene la información de lo que se presiono
        val action =
                //ProductsFragmentDirections.actionProductsFragmentToProductViewDetailsFragment(product)
                 MyPurchasesFragmentDirections.actionMyPurchaseFragmentToPurchaseDetailsFragment(product)
        findNavController().navigate(action)
    }

    private fun onShowErrorDataChange(error: DatabaseError) {
        Toast.makeText(
                activity,
                error.toException().message,
                Toast.LENGTH_LONG
        ).show()
    }

}