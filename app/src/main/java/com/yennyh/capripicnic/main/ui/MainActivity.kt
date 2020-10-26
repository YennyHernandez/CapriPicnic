package com.yennyh.capripicnic.main.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.auth.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var name: String = ""
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)


        val inputDataUser = intent.extras

        name = inputDataUser?.getString("name").toString()  //se obtiene la información
        email = inputDataUser?.getString("email").toString()
        password = inputDataUser?.getString("password").toString()

        welcome.text = "${welcome.text}\n$email"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutItem -> {  //item del menu
                sendLoguinData()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun sendLoguinData() {   //pasa la información y cambia de actividad
        val login = Intent(this, LoginActivity::class.java)
        login.putExtra("name:", name)
        login.putExtra("email", email)
        login.putExtra("password", password)
        startActivity(login)
        finish()
    }

    override fun onPause() {
        super.onPause()
        Log.d( "Metodo","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d( "Metodo","onStop")
    }
    override fun onBackPressed() {
        super.onBackPressed()
        onDestroy()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d( "Metodo","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d( "Metodo","onRestart")
    }


}


