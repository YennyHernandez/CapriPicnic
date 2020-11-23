package com.yennyh.capripicnic.ui.activities.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.server.Usuario
import com.yennyh.capripicnic.ui.activities.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var name: String = ""
    private var phone: String = ""
    private var email: String = ""
    private var password: String = ""
    private var passwordVerified: String = ""
    private var isValidName: Boolean = false
    private var isValidEmail: Boolean = false
    private var isValidPassword: Boolean = false
    private var isValidPasswordVerified: Boolean = false
    private var isAcceptTerms: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        //Setup
        setup()
        auth = FirebaseAuth.getInstance()
    }

    private fun setup() {
        termsRegister_textView.setOnClickListener {
            acceptTermsRegister_checkbox.isClickable = true
            acceptTermsRegister_checkbox.isEnabled = true
            termsRegister_textView.setTextColor(Color.parseColor("#ffffff")) //White
            MaterialAlertDialogBuilder(this)
                .setMessage("Abre el siguiente enlace para leer términos y condiciones. aquí")
                .setNeutralButton("Cancelar", null)
                .setNegativeButton("NO") { _, _ ->
                    acceptTermsRegister_checkbox.isChecked = false
                }
                .setPositiveButton("Aceptar") { _, _ ->
                    acceptTermsRegister_checkbox.isChecked = true
                }
                .show()
        }

        register_button.setOnClickListener {
            phone = phone_Register_editText.text.toString()
            name = nameRegister_editText.text.toString()
            isValidName = onValidName(name)
            email = emailRegister_editText.text.toString()
            isValidEmail = onValidEmail(email)
            password = passwordRegister_editText.text.toString()
            isValidPassword = onValidPassword(password)
            passwordVerified = passwordVerifiedRegister_editText.text.toString()
            isValidPasswordVerified = onValidPasswordVerified(passwordVerified)

            isAcceptTerms = if (!acceptTermsRegister_checkbox.isEnabled) {
                termsRegister_textView.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorAccent
                    )
                )
                false
            } else true

            if (isValidName && isValidEmail && isValidPassword && isValidPasswordVerified && isAcceptTerms) {
                register(email, password, name, phone)
            }else{
                Toast.makeText(
                    baseContext, "Ingrese todos los campos correctamente.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        singInRegister_textView.setOnClickListener {
            name = nameRegister_editText.text.toString()
            email = emailRegister_editText.text.toString()
            password = passwordRegister_editText.text.toString()
            passwordVerified = passwordVerifiedRegister_editText.text.toString()

            if (name.isEmpty() && email.isEmpty() && password.isEmpty() && passwordVerified.isEmpty()) {
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
    }

    private fun register(email: String, password: String, name: String, phone: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->   //si se realizo lo anterior continua
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid  //copia el id asociado a ese registro que crea firebase automaticamente
                    crearUsuarioEnBaseDeDatos(uid, email,name,phone)
                } else {
                    Toast.makeText(
                        baseContext, "Register failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun crearUsuarioEnBaseDeDatos(uid: String?, email: String, name: String, phone: String) {
        val database = FirebaseDatabase.getInstance()
        val myUsersReference =
            database.getReference("usuarios")   //me paro en la tabla que deseo y si no existe la crea
        //val id = myUsersReference.push().key //agrega id aleatoriamente pero como queriamos el mismo del que se resistro uso eol uid
        val usuario = Usuario(uid, name, email, phone)  //instancia el objeto (clase usuario)
        uid?.let {
            myUsersReference.child(uid).setValue(usuario)
        }  // mejor practica para no quitar la propiedade de null
        backToLogin()
    }

    private fun onValidName(name: String): Boolean {
        val usernamePattern = Pattern.compile(
            "^" +
                    ".{8,}" +               //at least 18 characters
                    "$",
        )

        val isValid = usernamePattern.matcher(name).matches()

        if (name.isEmpty()) {
            passwordRegister_textFieldLayout.error = "La contraseña es requerida!"
        } else if (!isValid) {
            nameRegister_textFieldLayout.error =
                "Ingrese al menos 10 carateres!" //TODO: Investigar como obtener el error
        } else {
            nameRegister_textFieldLayout.error = null
        }
        return isValid
    }

    private fun onValidEmail(email: String): Boolean {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (email.isEmpty()) {
            emailRegister_textFieldLoyout.error = "El email es requerido!"
        } else if (!isValid) {
            emailRegister_textFieldLoyout.error = "Ingrese un email válido!"
        } else {
            emailRegister_textFieldLoyout.error = null
        }
        return isValid
    }

    private fun onValidPassword(password: String): Boolean {
        val passwordPattern = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$",
        )
        val isValid = passwordPattern.matcher(password).matches()

        if (password.isEmpty()) {
            passwordRegister_textFieldLayout.error = "La contraseña es requerida!"
        } else if (!isValid) {
            passwordRegister_textFieldLayout.error =
                "Ingrese al menos 8 caracteres que contenga un letra minuscula, una mayuscula, un numero, un caracter especial!" //TODO: Investigar como obtener el error
        } else {
            passwordRegister_textFieldLayout.error = null
        }
        return isValid
    }

    private fun onValidPasswordVerified(passwordV: String): Boolean {
        val isValid = passwordV == password

        if (password.isNotEmpty()) {
            if (!isValid) {
                passwordVerifiedRegister_textFieldLayout.error = "Las contraseñas no coinciden"
            } else {
                passwordVerifiedRegister_textFieldLayout.error = null
            }
        } else {
            passwordVerifiedRegister_textFieldLayout.error = "La contraseña de verificación es requida!"
        }
        return isValid
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


