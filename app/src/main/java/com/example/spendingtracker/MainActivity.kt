package com.example.spendingtracker

import android.app.Notification
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.AsyncTask.execute
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.example.spendingtracker.RealmObjects.CategoryObject
import io.realm.Realm
import io.realm.Realm.Transaction
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mes = MessageReceiver()
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

        Realm.init(applicationContext)
        initialRealmObjects()
        initialReamTransation()
//        val categoryObject = CategoryObject()
//        categoryObject.name = "lawrence"
//        categoryObject.id = 1
//
     //    val realm = Realm.getDefaultInstance()

//        val realmConfig = RealmConfiguration.Builder()
//            .schemaVersion(0)
//            .build()
//        Realm.deleteRealm(realmConfig)
//        Realm.setDefaultConfiguration(realmConfig)

        loadFragment(HomeFragment.newInstance())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initialRealmObjects(){
        val realmConfig =  RealmConfiguration.Builder()
            .name("categoty.realm").schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }

    private fun initialReamTransation(){

        val sharedpref = getSharedPreferences("com.example.spendingtracker", Context.MODE_PRIVATE)
        Toast.makeText(applicationContext,sharedpref.getBoolean("firstRun",true).toString(),Toast.LENGTH_LONG).show()
        if(sharedpref.getBoolean("firstRun",true)){

            val titles = arrayOf("Purchase","Withdrawal","Transfer","Bills","Transport","Entertainment")
            val realm = Realm.getDefaultInstance()

            for (title in titles){
                realm.beginTransaction()
                var realmObj =  realm.createObject(CategoryObject().javaClass)
                realmObj.name = title
                realm.commitTransaction()
            }
           realm.close()

            sharedpref.edit().putBoolean("firstRun",false).commit()
       }

    }

}
