package com.example.spendingtracker

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, intent.action, Toast.LENGTH_LONG).show()
        val alert = AlertDialog.Builder(context)
        alert.setMessage("Sms receiver is working").create().show()

//        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
//
//        }
    }
}