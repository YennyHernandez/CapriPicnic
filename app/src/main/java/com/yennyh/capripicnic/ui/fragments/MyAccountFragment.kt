package com.yennyh.capripicnic.ui.fragments

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.yennyh.capripicnic.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_my_account.*

class MyAccountFragment : Fragment() {

    private var ctx: Context? = null
    private var self: View? = null
    private var onUpdate = false
    private  var documentTypeLayout : TextInputLayout? = null
    private  var documentTypeDropDownText: AutoCompleteTextView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Setup
        setup()

        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

    private fun setup() {
//        updateUser_checkbox_accountView.setOnClickListener {
////            updateUser_checkbox_accountView.isChecked = onUpdate
//            updateUser_checkbox_accountView.setTextColor(Color.parseColor("#ffffff")) //White
//            MaterialAlertDialogBuilder(requireContext())
//                .setMessage("Desea modicar los campos del formulario?")
//                .setNeutralButton("Cancelar", null)
//                .setNegativeButton("NO") { _, _ ->
////                    updateUser_checkbox_accountView.isChecked = false
//                    currentPasswordAccount_textFieldLayout.visibility = View.GONE
//                    newPassword_accountView_layout.visibility = View.GONE
//                    update_button_accountView.visibility = View.GONE
//                    onUpdate = false
//                }
//                .setPositiveButton("Aceptar") { _, _ ->
////                    updateUser_checkbox_accountView.isChecked = true
//                    onUpdate = true
//                    currentPasswordAccount_textFieldLayout.visibility = View.VISIBLE
//                    newPassword_accountView_layout.visibility = View.VISIBLE
//                    update_button_accountView.visibility = View.VISIBLE
//                }
//                .show()
//        }

    }

}