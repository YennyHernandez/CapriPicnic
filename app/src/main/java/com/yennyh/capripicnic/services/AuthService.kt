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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.models.User
import com.yennyh.capripicnic.ui.activities.login.LoginActivity
import com.yennyh.capripicnic.ui.activities.main.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch


open class AuthService : AppCompatActivity() {
    private val googleSignInID = 100

    fun newUser(newUser: User) {
        newUser.password?.let { password ->
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(newUser.email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        mailVerify()
                        newUser.password = null

                        setUserDatabase(newUser)
                    } else {
                        Toast.makeText(baseContext, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun setUserDatabase(newUser: User) {
        val currentUser = getCurrentUser()
        val uid = currentUser.uid
        newUser.uid = uid
        val usersReference =
            newUser.uid?.let { FirebaseDatabase.getInstance().getReference("users").child(it) }
        usersReference?.setValue(newUser)?.addOnCompleteListener { task ->
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

    private fun mailVerify() {
        val user = getCurrentUser()
        user.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    baseContext,
                    "Por favor revise correo para verificar cuenta",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    baseContext,
                    "Error! " + it.exception?.message,
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser: FirebaseUser = getCurrentUser()

                    if (currentUser.isEmailVerified) {
                        val userReference = FirebaseDatabase.getInstance().getReference("users")
                            .child(currentUser.uid)

                        val postListener = object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val user: User = dataSnapshot.getValue(User::class.java)!!
                                user.verified = true
                                userReference.setValue(user)
                                editPrefs(email, "BASIC")
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Toast.makeText(
                                    baseContext,
                                    databaseError.toException().message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        userReference.addValueEventListener(postListener)

                        val main = Intent(this, MainActivity::class.java)
                        startActivity(main)
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Por favor verifique la cuenta en su correo!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

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
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            account.email?.let { email -> editPrefs(email, credential.provider) }

                            val newUser = User()
                            newUser.name = account.givenName.toString()
                            newUser.lastName = account.familyName.toString()
                            newUser.email = account.email.toString()
                            newUser.provider = credential.provider
                            newUser.displayName = account.displayName
                            newUser.verified = true
                            newUser.uid = account.id
                            newUser.photoUrl = account.photoUrl.toString()

                            setUserDatabase(newUser)
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

    fun getCurrentUser(): FirebaseUser {
        return FirebaseAuth.getInstance().currentUser!!
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        clearPrefs()
        goToLogin()
    }


    fun userExist(): Boolean{
        var exist = true //TODO: Inicializar en false
        val userReference =
            getCurrentUser().let {
                FirebaseDatabase.getInstance().getReference("users").child(it.uid)
            }
        GlobalScope.launch {
            val postListener = object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        exist = true //TODO: No está alcanzando a cambiar el valor, buscar análogo a async await
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        baseContext,
                        databaseError.toException().message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            userReference.addValueEventListener(postListener)
        }

        return exist
    }

    fun editPrefs(email: String, provider: String) {
        val prefs = getSharedPreferences(
            getString(R.string.prefs_file),
            Context.MODE_PRIVATE
        ).edit()
        val putString = prefs.putString(
            "email",
            email ?: ""
        )
        prefs.putString("provider", provider)
        prefs.apply()
    }

    fun clearPrefs(){
        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
    }

    fun goToLogin() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}