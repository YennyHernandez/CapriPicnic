package com.yennyh.capripicnic.ui.activities.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.services.AuthService
import com.yennyh.capripicnic.ui.activities.main.MainActivity
import com.yennyh.capripicnic.ui.activities.passwordrecovery.PasswordRecoveryActivity
import com.yennyh.capripicnic.ui.activities.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

class LoginActivity : AuthService() {

    private var email: String = ""
    private var password: String = ""
    private var isValidEmail: Boolean = false
    private var isValidPassword: Boolean = false
    private var emailOnFirstLeaveFocus: Boolean = false
    private var passwordOnFirstLeaveFocus: Boolean = false
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        //Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración con Firebase completa")
        analytics.logEvent("InitScreen", bundle)

        //Setup
        setup()
        session()
    }

    override fun onStart() {
        super.onStart()

        authLayout.visibility = View.VISIBLE
    }

    private fun session() {

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null) {
            if(userExist()){
                authLayout.visibility = View.INVISIBLE
                sendMainActivity()
            }
        }
    }

    private fun setup() {
        email_editText.addTextChangedListener(textWatcher)
        password_editText.addTextChangedListener(textWatcher)

        forgotPassword_textView.setOnClickListener {
            val passwordRecovery = Intent(this, PasswordRecoveryActivity::class.java)
            startActivity(passwordRecovery)
        }

        signIn_button.setOnClickListener {
            email = email_editText.text.toString()
            isValidEmail = onValidEmail(email)
            password = password_editText.text.toString()
            isValidPassword = onValidPassword(password)

            if (isValidEmail && isValidPassword) {
                signInWithEmailAndPassword(email, password)
            }
        }

        signIn_withGoogle_button.setOnClickListener {
            signInWithGoogle()
        }

        singUp_textView.setOnClickListener {
            email = email_editText.text.toString()
            password = password_editText.text.toString()

            if (email.isEmpty() && password.isEmpty()) {
                startActivity(Intent(this, RegisterActivity::class.java))
            } else {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Está seguro?")
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Aceptar") { _, _ ->
                        startActivity(Intent(this, RegisterActivity::class.java))
                    }
                    .show()
            }
        }

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //TODO: Obtener el textView que llamó al envento, crear bandera para que simular onDirty despues de activar un onfocusLeave
            email_editText.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!emailOnFirstLeaveFocus) {
                            password = password_editText.text.toString()
                            isValidPassword = onValidPassword(password)
                        }
                        emailOnFirstLeaveFocus = true
                    }
                }

            password_editText.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!passwordOnFirstLeaveFocus) {
                            email = email_editText.text.toString()
                            isValidEmail = onValidEmail(email)
                        }
                        passwordOnFirstLeaveFocus = true
                    }
                }

            if (emailOnFirstLeaveFocus) {
                email = email_editText.text.toString()
                isValidEmail = onValidEmail(email)
            }
            if (passwordOnFirstLeaveFocus) {
                password = password_editText.text.toString()
                isValidPassword = onValidPassword(password)
            }
        }
    }

    private fun onValidEmail(email: String): Boolean {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (email.isEmpty()) {
            email_textFieldLayout.error = "El email es requerido!"
        } else if (!isValid) {
            email_textFieldLayout.error = "Debe ingresar un email valido! (@example.co)"
        } else {
            email_textFieldLayout.error = null
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
            password_textFieldLayout.error = "La contraseña es requerida!"
        } else if (!isValid) {
            password_textFieldLayout.error =
                "Ingrese al menos 8 caracteres que contenga un letra minuscula, una mayuscula, un numero, un caracter especial!" //TODO: Investigar como obtener el error
        } else {
            password_textFieldLayout.error = null
        }
        return isValid
    }

    private fun sendMainActivity() {
        val main = Intent(this, MainActivity::class.java)
        startActivity(main)
        finish()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            val exit = Intent(Intent.ACTION_MAIN)
            exit.addCategory(Intent.CATEGORY_HOME)
            startActivity(exit)
            finish()
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            run {
                doubleBackToExitPressedOnce = false
            }
        }, 2000)
    }

}