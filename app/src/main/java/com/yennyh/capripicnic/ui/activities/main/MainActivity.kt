package com.yennyh.capripicnic.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.services.AuthService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AuthService() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.main_nav_host) //Initialising navController

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.myQuotesFragment, R.id.myPurchaseFragment,
            R.id.myReservationsFragment, R.id.myAccountFragment,
            R.id.homeFragment, R.id.reservationsFragment,
            R.id.productsFragment, R.id.mapsFragment
        ) //Pass the ids of fragments from nav_graph which you d'ont want to show back button in toolbar
            .setOpenableLayout(drawer_layout) //Pass the drawer layout id from activity xml
            .build()

        setSupportActionBar(main_toolbar) //Set toolbar

        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        ) //Setup toolbar with back button and drawer icon according to appBarConfiguration

        visibilityNavElements(navController) //If you want to hide drawer or bottom navigation configure that in this function
    }

    private fun visibilityNavElements(navController: NavController) {

        //Listen for the change in fragment (navigation) and hide or show drawer or bottom navigation accordingly if required
        //Modify this according to your need
        //If you want you can implement logic to deselect(styling) the bottom navigation menu item when drawer item selected using listener

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.myQuotesFragment,
                R.id.myPurchaseFragment,
                R.id.myReservationsFragment,
                R.id.myAccountFragment -> hideBothNavigation()
                else -> showBothNavigation()
            }
        }

    }

    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        main_bottom_navigation_view?.visibility = View.GONE
        main_navigation_view?.visibility = View.GONE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun showBothNavigation() {
        main_bottom_navigation_view?.visibility = View.VISIBLE
        main_navigation_view?.visibility = View.VISIBLE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl() //To configure navController with drawer and bottom navigation
    }

    private fun setupNavControl() {
        main_navigation_view?.setupWithNavController(navController) //Setup Drawer navigation with navController
        main_bottom_navigation_view?.setupWithNavController(navController) //Setup Bottom navigation with navController
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.overflowMenu_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onBackPressed() {
        when { //If drawer layout is open close that on back pressed
            drawer_layout.isDrawerOpen(GravityCompat.START) -> {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            main_bottom_navigation_view.isVisible -> {
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
            else -> {
                super.onBackPressed() //If drawer is already in closed condition then go back
            }
        }
    }
}
