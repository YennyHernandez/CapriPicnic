package com.yennyh.capripicnic.ui.activities.passwordrecovery


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yennyh.capripicnic.R
import kotlinx.android.synthetic.main.activity_password_recovery.*

class PasswordRecoveryActivity : AppCompatActivity() {

    private var email: String = ""
    private var isValidEmail: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_recovery)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        //Setup
        setup()
    }

    private fun setup() {
        emailRecovery_editText.addTextChangedListener(textWatcher)

        submit_button.setOnClickListener {
            email = this.emailRecovery_editText.text.toString()

            if (onValidEmail(email)) {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Check $email, to recovery your account")
                    .setPositiveButton("OK") { _, _ ->
                        emailRecovery_editText.text = null
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
            email = emailRecovery_editText.text.toString()
            isValidEmail = onValidEmail(email)
        }
    }

    private fun onValidEmail(email: String): Boolean {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (email.isEmpty()) {
            emailRecovery_textFieldLayout.error = "Email is required!"
        } else if (!isValid) {
            emailRecovery_textFieldLayout.error = "You must enter a valid email!"
        } else {
            emailRecovery_textFieldLayout.error = null
        }
        return isValid
    }
}