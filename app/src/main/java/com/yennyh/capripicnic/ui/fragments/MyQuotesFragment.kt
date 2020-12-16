package com.yennyh.capripicnic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.databinding.FragmentMyQuotesBinding
import com.yennyh.capripicnic.models.*
import com.yennyh.capripicnic.services.AuthService
import com.yennyh.capripicnic.shared.lists_adapters.MyQuoteViewRVAdapter
import androidx.navigation.fragment.findNavController

class MyQuotesFragment : Fragment(), MyQuoteViewRVAdapter.OnItemClickListener {
    private lateinit var contentRVAdapter: MyQuoteViewRVAdapter
    private lateinit var binding: FragmentMyQuotesBinding
    private lateinit var authSvc: AuthService

    var listMyQuotes: MutableList<Product> =
        mutableListOf()   //usado para bases de datos con firebaserealtime

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {// Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_my_quotes,
            container,
            false
        ) //purchase contiene picnics y productos comprados
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        authSvc = AuthService()
        binding = FragmentMyQuotesBinding.bind(view)
        binding.myQuotesRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.myQuotesRecyclerView.setHasFixedSize(true)
        contentRVAdapter = MyQuoteViewRVAdapter(
            listMyQuotes as ArrayList<Product>,
            this@MyQuotesFragment
        )
        binding.myQuotesRecyclerView.adapter =
            contentRVAdapter   // hay que cargarle la información
        loadFromFirebase()
        contentRVAdapter.notifyDataSetChanged()
    }

    private fun loadFromFirebase() {
        listMyQuotes.clear()

        val currentUserUid = authSvc.getCurrentUser().uid
        val database = FirebaseDatabase.getInstance()   //instancia
        val myCurrentUserRef = database.getReference("users").child(currentUserUid)  //referencia

        val postListenerUserDB =
            object :
                ValueEventListener {  //hace un llamado y devuelve la información que contiene el usuario
                override fun onDataChange(dataSnapshotUser: DataSnapshot) {  //snaps es la data del usuario
                    val user: User = dataSnapshotUser.getValue(User::class.java)!!

                    for (cartId: IdCart in user.idCarts!!) {
                        val myCartRef = database.getReference("carts").child(cartId.id)

                        val postListenerCartDB =
                            object : ValueEventListener {
                                override fun onDataChange(dataSnapshotCart: DataSnapshot) {
                                    val cart: Cart? = dataSnapshotCart.getValue(Cart::class.java)!!

                                    if (cart != null) {
                                        for (productsId: IdProduct in cart.idProducts) {

                                            val myProductRef =
                                                database.getReference("products")  //referencia
//
                                            val postListener =
                                                object :
                                                    ValueEventListener {  //hace un llamado y devuelve la información que contiene
                                                    override fun onDataChange(dataSnapshotProduct: DataSnapshot) {  //snaps es la data
                                                        for (data: DataSnapshot in dataSnapshotProduct.children) { // recorrera todos los hijos (items de tabla)
                                                            val product =
                                                                data.getValue(Product::class.java)  //lo guarda en una variable
                                                            if (product?.id == productsId.id) {
                                                                listMyQuotes.add(product)
                                                            }
                                                        }
                                                        contentRVAdapter.notifyDataSetChanged()
                                                    }

                                                    override fun onCancelled(error: DatabaseError) {
                                                    }
                                                }

                                            myProductRef.addValueEventListener(postListener)  //agrega la información cargada
                                        }
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
        myCurrentUserRef.addValueEventListener(postListenerUserDB)
    }

    override fun onItemClick(product: Product) {  //obtiene la información de lo que se presiono
        val action = MyQuotesFragmentDirections.actionMyQuotesFragmentToMyQuoteDetailsFragment(product)
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