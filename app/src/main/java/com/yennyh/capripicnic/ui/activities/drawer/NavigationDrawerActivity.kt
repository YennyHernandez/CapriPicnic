package com.yennyh.capripicnic.ui.activities.drawer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.ui.activities.login.LoginActivity

class NavigationDrawerActivity : AppCompatActivity() {

    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var doubleBackToExitPressedOnce = false


    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navegation_drawer)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        val inputDataUser = intent.extras

        this.name = inputDataUser?.getString("name").toString()  //se obtiene la información
        this.email = inputDataUser?.getString("email").toString()
        this.password = inputDataUser?.getString("password").toString()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)


        val navigationView = this.findViewById<View>(R.id.nav_view) as NavigationView
        val header = navigationView.getHeaderView(0)
        val emailNavHeader: TextView = header.findViewById(R.id.email_navHeader)
        emailNavHeader.text = email

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_products,
                R.id.nav_myQuotes,
                R.id.nav_myReservations
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.overflowMenu_logout -> {  //item del menu
                sendLoguinData()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun sendLoguinData() {   //pasa la información y cambia de actividad
        val login = Intent(this, LoginActivity::class.java)
        login.putExtra("name:", name)
        login.putExtra("email", email)
        login.putExtra("password", password)
        startActivity(login)
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