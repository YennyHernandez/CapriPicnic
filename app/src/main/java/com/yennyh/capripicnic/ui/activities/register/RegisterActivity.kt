package com.yennyh.capripicnic.ui.activities.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
    private var nameOnFirstLeaveFocus: Boolean = false
    private var emailOnFirstLeaveFocus: Boolean = false
    private var passwordOnFirstLeaveFocus: Boolean = false
    private var passwordVerifiedOnFirstLeaveFocus: Boolean = false
    private var isAcceptTerms: Boolean = false
    companion object {

        private val TAG = RegisterActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        //Setup
        setup()
        auth = FirebaseAuth.getInstance()
    }

    private fun setup() {
//        nameRegister_editText.addTextChangedListener(textWatcher)
//        emailRegister_editText.addTextChangedListener(textWatcher)
//        passwordRegister_editText.addTextChangedListener(textWatcher)
//        passwordVerifiedRegister_editText.addTextChangedListener(textWatcher)

        termsRegister_textView.setOnClickListener {
            acceptTermsRegister_checkbox.isClickable = true
            acceptTermsRegister_checkbox.isEnabled = true
            termsRegister_textView.setTextColor(Color.parseColor("#ffffff")) //White
            MaterialAlertDialogBuilder(this)
                .setMessage("Estás seguro?")
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
                registroEnFirebase(email, password, name, phone)
                register()
            }
        }

        singInRegister_textView.setOnClickListener {
            name = nameRegister_editText.text.toString()
            email = emailRegister_editText.text.toString()
            password = passwordRegister_editText.text.toString()
            passwordVerified = passwordVerifiedRegister_editText.text.toString()

            if (name.isEmpty() && email.isEmpty() && password.isEmpty() && passwordVerified.isEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Estás seguro?")
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Aceptar") { _, _ ->
                        sendLoguinData()

                    }
                    .show()
            }
        }
    }

    private fun registroEnFirebase(email: String, password: String, name: String, phone: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->   //si se realizo lo anterior continua
                if (task.isSuccessful) {
                    val uid =
                        auth.currentUser?.uid  //copia el id asociado a ese registro que crea firebase automaticamente
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    CrearUsuarioEnBaseDeDatos(uid, email,name,phone)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun CrearUsuarioEnBaseDeDatos(uid: String?, email: String, name: String, phone: String) {
        val database = FirebaseDatabase.getInstance()
        val myUsersReference =
            database.getReference("usuarios")   //me paro en la tabla que deseo y si no existe la crea
        //val id = myUsersReference.push().key //agrega id aleatoriamente pero como queriamos el mismo del que se resistro uso eol uid
        val usuario = Usuario(uid, name, email, this.phone)  //instancia el objeto (clase usuario)
        uid?.let {
            myUsersReference.child(uid).setValue(usuario)
        }  // mejor practica para no quitar la propiedade de null
        onBackPressed()
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            nameRegister_editText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!nameOnFirstLeaveFocus) {
                            email = emailRegister_editText.text.toString()
                            password = passwordRegister_editText.text.toString()
                            passwordVerified = passwordVerifiedRegister_editText.text.toString()
                            isValidEmail = onValidEmail(email)
                            isValidPassword = onValidPassword(password)
                            isValidPasswordVerified = onValidPasswordVerified(passwordVerified)
                        }
                        nameOnFirstLeaveFocus = true
                    }
                }

            emailRegister_editText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!emailOnFirstLeaveFocus) {
                            name = nameRegister_editText.text.toString()
                            password = passwordRegister_editText.text.toString()
                            passwordVerified = passwordVerifiedRegister_editText.text.toString()
                            isValidName = onValidName(name)
                            isValidPassword = onValidPassword(password)
                            isValidPasswordVerified = onValidPasswordVerified(passwordVerified)
                        }
                        emailOnFirstLeaveFocus = true
                    }
                }

            passwordRegister_editText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!passwordOnFirstLeaveFocus) {
                            name = nameRegister_editText.text.toString()
                            email = emailRegister_editText.text.toString()
                            passwordVerified = passwordVerifiedRegister_editText.text.toString()
                            isValidName = onValidName(name)
                            isValidEmail = onValidEmail(password)
                            isValidPasswordVerified = onValidPasswordVerified(passwordVerified)
                        }
                        passwordOnFirstLeaveFocus = true
                    }
                }

            passwordVerifiedRegister_editText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!passwordVerifiedOnFirstLeaveFocus) {
                            name = nameRegister_editText.text.toString()
                            email = emailRegister_editText.text.toString()
                            password = passwordRegister_editText.text.toString()
                            isValidName = onValidName(name)
                            isValidEmail = onValidEmail(password)
                            isValidPassword = onValidPassword(password)
                        }
                        passwordOnFirstLeaveFocus = true
                    }
                }

            if (nameOnFirstLeaveFocus) {
                name = nameRegister_editText.text.toString()
                isValidName = onValidName(name)
            }
            if (emailOnFirstLeaveFocus) {
                email = emailRegister_editText.text.toString()
                isValidEmail = onValidEmail(email)
            }
            if (passwordOnFirstLeaveFocus) {
                password = passwordRegister_editText.text.toString()
                isValidPassword = onValidPassword(password)
            }
            if (passwordVerifiedOnFirstLeaveFocus) {
                passwordVerified = passwordVerifiedRegister_editText.text.toString()
                isValidPassword = onValidPasswordVerified(passwordVerified)
            }
        }
    }

    private fun onValidName(name: String): Boolean {
        val usernamePattern = Pattern.compile(
            "^" +
//                    "(?=\\.*[0-9])" +         //no digits
//                    "(?=\\.*[@#$%^&+=])" +    //no special character
                    ".{10,}" +               //at least 8 characters
                    "$",
        )

        val isValid = usernamePattern.matcher(name).matches()

        if (name.isEmpty()) {
            passwordRegister_textFieldLayout.error = "La contraseña es requerida!"
        } else if (!isValid) {
            nameRegister_textFieldLayout.error =
                "El nombre no puede contener caracteres especuiles y al menos 10!" //TODO: Investigar como obtener el error
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
                "La contraseña debe conterner al menos un letra minuscula, una mayuscula un numero, un caracter especial y al menos 8 caracteres!" //TODO: Investigar como obtener el error
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

    private fun register() {
        MaterialAlertDialogBuilder(this)
            .setMessage(
                "Data user\n\n" +
                        "Name: $name\n" +
                        "Email: $email\n" +
                        "Password: $password\n\n"
            )
            .setNegativeButton("OK", null)
            .show()
    }

    override fun onBackPressed() {
        sendLoguinData()
    }

    private fun sendLoguinData() {
        val login = Intent(this, LoginActivity::class.java)
        /*login.putExtra("name:", name)
        login.putExtra("email", email)
        login.putExtra("password", password)*/
        startActivity(login)
        finish()
    }


}


