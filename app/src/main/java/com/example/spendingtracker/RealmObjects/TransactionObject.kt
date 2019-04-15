package com.example.spendingtracker.RealmObjects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.text.DecimalFormat
import java.util.*

open class TransactionObject :RealmObject()
{
    @PrimaryKey
    var id : String = ""

    @Required
    var transactionDate : String = ""

    var transactionAmount :Float = 0.0F

    var category: CategoryObject? =  CategoryObject()
}