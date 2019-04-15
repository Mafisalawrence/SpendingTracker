package com.example.spendingtracker

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask.execute
import android.os.Bundle
import android.provider.Telephony
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Range
import android.widget.Button
import android.widget.Toast
import com.example.spendingtracker.RealmObjects.CategoryObject
import com.example.spendingtracker.RealmObjects.TransactionObject
import io.realm.Realm
import io.realm.Realm.Transaction
import io.realm.Realm.compactRealm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

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
        initialReamTransaction()
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS),1)
        }
        readInboxMessage(this)
        loadFragment(HomeFragment.newInstance())

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initialRealmObjects(){
        val realmConfig =  RealmConfiguration.Builder()
            .name("SpendingTracker.realm").schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build()
      Realm.setDefaultConfiguration(realmConfig)
    }

    private fun initialReamTransaction(){

        val sharedpref = getSharedPreferences("com.example.spendingtracker", Context.MODE_PRIVATE)
        if(sharedpref.getBoolean("firstRun",true)){

            val titles = arrayOf("Purchase","Withdrawal","Transfer","Bills","Transport","Entertainment","Other")
            val realm = Realm.getDefaultInstance()

            for (title in titles){
                realm.beginTransaction()
                var realmObj =  realm.createObject(CategoryObject().javaClass, UUID.randomUUID().toString())
                realmObj.categoryName = title
                realm.commitTransaction()
            }
           realm.close()

            sharedpref.edit().putBoolean("firstRun",false).commit()
       }
   }
    fun readInboxMessage(context: Context){
        val transactionObjects = arrayListOf<TransactionObject>()
        val realm = Realm.getDefaultInstance()
        val cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
        cursor.moveToFirst()
        var lastMonth = 0
        do {
            val messageBody =  cursor.getString(cursor.getColumnIndexOrThrow("body")).toString()
            if (messageBody.startsWith("Capitec") && messageBody.contains("Avail")){
                val messageDate =  cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)).toString()
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = messageDate.toLong()
                val formattedDate = java.text.SimpleDateFormat("MM/dd/yyyy").format(calendar.time)
                transactionObjects.add(getTransactionDetails(this, messageBody,formattedDate,realm))
                lastMonth = Integer.parseInt(formattedDate.split('/')[0])
            }
        }
        while ( cursor.moveToNext() && lastMonth >= 4)

        for (transactionObject in transactionObjects) {
            realm.beginTransaction()

            var realmObj =  realm.createObject(TransactionObject().javaClass, UUID.randomUUID().toString())
            realmObj.category = realm.copyFromRealm(transactionObject.category)
            realmObj.transactionAmount = transactionObject.transactionAmount
            realmObj.transactionDate = transactionObject.transactionDate
            realm.commitTransaction()
        }
        realm.close()
        Toast.makeText(context,transactionObjects.count().toString(),Toast.LENGTH_LONG).show()
    }
    private fun getTransactionDetails(context: Context, message: String, date: String,realm: Realm):TransactionObject{

        val firstSplit = message.split(';')
        val details = firstSplit.firstOrNull{ x -> x.contains("Capitec")}?.split(' ')
        val amountString = details?.firstOrNull{ x -> x.startsWith('+') || x.startsWith('-') }

        val transaction = TransactionObject()
        transaction.transactionAmount = amountString?.substringAfter('R')!!.toFloat()
        transaction.transactionDate = date
        transaction.category = getTransactionCategory(firstSplit[0],realm)
        return transaction

    }
    private fun getTransactionCategory(transactionDetails: String,realm: Realm):CategoryObject
    {
realm.beginTransaction()
        var categoryObject = CategoryObject()
        val details = transactionDetails.split(' ')

        if (details[1] == "Purchase"){
            categoryObject = realm.where(CategoryObject().javaClass).equalTo("categoryName","Purchase").findFirst()!!
        }else if (details[1] == "Transfer" ||(details[1] == "Money" && details[2] == "Out")) {
            categoryObject= realm.where(CategoryObject().javaClass).equalTo("categoryName","Transfer").findFirst()!!
        }else if (details[1] == "Debit") {
            categoryObject = realm.where(CategoryObject().javaClass).equalTo("categoryName", "Bills").findFirst()!!
        }else if (details[1] == "Cash"){
            categoryObject = realm.where(CategoryObject().javaClass).equalTo("categoryName","Withdrawal").findFirst()!!
        }else
            categoryObject = realm.where(CategoryObject().javaClass).equalTo("categoryName","Other").findFirst()!!
        realm.commitTransaction()
        return  categoryObject

    }

}
