package com.example.spendingtracker

import android.app.Notification
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(HomeFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                loadFragment(DashboardFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                loadFragment(NotificationFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    private fun loadFragment(fragment:Fragment){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment.newInstance())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
