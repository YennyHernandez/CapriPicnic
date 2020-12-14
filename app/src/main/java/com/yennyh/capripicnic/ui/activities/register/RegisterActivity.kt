package com.yennyh.capripicnic.ui.activities.register

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.models.MatFormField
import com.yennyh.capripicnic.models.Patterns
import com.yennyh.capripicnic.models.RegisterForm
import com.yennyh.capripicnic.models.User
import com.yennyh.capripicnic.services.AuthService
import com.yennyh.capripicnic.services.FormsValidator
import com.yennyh.capripicnic.ui.activities.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class RegisterActivity : AuthService() {
    private var isAcceptTerms = false

    private lateinit var documentTypeLayout: TextInputLayout
    private lateinit var documentTypeDropDownText: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        documentTypeLayout = documentType_TextInputLayout
        documentTypeDropDownText = documentType_AutocompleteTextView

        val res: Resources = resources
        val documentTypeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            res.getStringArray(R.array.document_type_options_list)
        )
        documentTypeDropDownText.setAdapter(documentTypeAdapter)

        //Setup
        setup()
    }


    private fun setup() {

        acceptTermsRegister_checkbox.setOnClickListener {
            acceptTermsRegister_checkbox.isChecked = isAcceptTerms
            acceptTermsRegister_checkbox.setTextColor(Color.parseColor("#ffffff")) //White
            MaterialAlertDialogBuilder(this)
                .setMessage("Acepta términos y condiciones?")
                .setNeutralButton("Cancelar", null)
                .setNegativeButton("NO") { _, _ ->
                    acceptTermsRegister_checkbox.isChecked = false
                    isAcceptTerms = false
                }
                .setPositiveButton("Aceptar") { _, _ ->
                    acceptTermsRegister_checkbox.isChecked = true
                    isAcceptTerms = true
                }
                .show()
        }

        register_button.setOnClickListener {
            isAcceptTerms = if (!acceptTermsRegister_checkbox.isChecked) {
                acceptTermsRegister_checkbox.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorAccent
                    )
                )
                false
            } else true

            if (isAcceptTerms) {
                newUser(getFormValue())
            } else {
                Toast.makeText(
                    baseContext, "Ingrese todos los campos correctamente.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        singInRegister_textView.setOnClickListener {

            if (!isAcceptTerms) {
                backToLogin()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Estás seguro?")
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Aceptar") { _, _ ->
                        backToLogin()
                    }
                    .show()
            }
        }

        signIn_withGoogle_button.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun getFormValue(): User{
        val newUser = User()
        newUser.name = nameRegister_editText.text.toString()
        newUser.lastName = lastNameRegister_editText.text.toString()
        newUser.documentType =  documentType_AutocompleteTextView.text.toString()
        newUser.documentNumber = documentNumber_editText.text.toString().toLong()
        newUser.email = emailRegister_editText.text.toString()
        newUser.phone = phoneRegister_editText.text.toString().toLong()
        newUser.address = addressRegister_editText.text.toString()
        newUser.password = passwordRegister_editText.text.toString()
        return  newUser
    }


    override fun onBackPressed() {
        backToLogin()
    }

    private fun backToLogin() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }

}


