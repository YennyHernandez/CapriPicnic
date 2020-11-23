package com.yennyh.capripicnic.ui.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yennyh.capripicnic.R
import com.yennyh.capripicnic.ui.activities.navigation.bottom.BottomNavigationActivity
import com.yennyh.capripicnic.ui.activities.navigation.drawer.NavigationDrawerActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        val drawer= Intent(this, NavigationDrawerActivity::class.java)
        startActivity(drawer)
//        val bottom= Intent(this, BottomNavigationActivity::class.java)
//        startActivity(bottom)
    }
}