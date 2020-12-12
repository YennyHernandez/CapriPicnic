package com.yennyh.capripicnic.services

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.models.User
import com.yennyh.capripicnic.ui.activities.login.LoginActivity
import com.yennyh.capripicnic.ui.activities.main.MainActivity


enum class Providers {
    BASIC,
    GOOGLE
}

open class AuthService : AppCompatActivity() {
    private val googleSignInID = 100
    lateinit var auth: FirebaseAuth

    fun newUser(user: User) {
        user.password?.let {
            auth.createUserWithEmailAndPassword(user.email, it)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        mailVerify()
                        val currentUser = getCurrentUser()

                        val uid = currentUser!!.uid
                        val usersReference =
                            FirebaseDatabase.getInstance().getReference("users").child(uid)

                        user.uid = uid
                        uid.let {
                            usersReference.setValue(user).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        baseContext, "Register successful!.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    goToLogin()
                                } else {
                                    Toast.makeText(
                                        baseContext, task.exception?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                    } else {
                        Toast.makeText(baseContext, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    fun mailVerify() {
        val user = getCurrentUser()
        user?.sendEmailVerification()?.addOnCompleteListener {
            Toast.makeText(
                baseContext,
                "Por favor revice correo para verificar cuenta",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val prefs = getSharedPreferences(
                        getString(R.string.prefs_file),
                        Context.MODE_PRIVATE
                    ).edit()
                    prefs.putString("email", email)
                    prefs.putString("provider", "BASIC")
                    prefs.apply()
                    val main = Intent(this, MainActivity::class.java)
                    startActivity(main)
                } else {
                    Toast.makeText(baseContext, it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    fun signInWithGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()

        startActivityForResult(googleClient.signInIntent, googleSignInID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == googleSignInID) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val prefs = getSharedPreferences(
                                    getString(R.string.prefs_file),
                                    Context.MODE_PRIVATE
                                ).edit()
                                val putString = prefs.putString(
                                    "email",
                                    account.email ?: ""
                                )
                                prefs.putString("provider", "Google")
                                prefs.apply()

                                val main = Intent(this, MainActivity::class.java)
                                startActivity(main)
                                finish()
                            } else {
                                Toast.makeText(
                                    baseContext, it.exception?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            } catch (error: ApiException) {
                Toast.makeText(
                    baseContext, error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun updateUser(uid: String, user: User) {

    }

    fun deleteUser(uidUser: String) {

    }

    fun changePassword(currentPassword: String, newPassword: String) {
        FirebaseAuth.getInstance().confirmPasswordReset(currentPassword, newPassword)
    }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()

        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        goToLogin()
    }

    fun goToLogin() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}