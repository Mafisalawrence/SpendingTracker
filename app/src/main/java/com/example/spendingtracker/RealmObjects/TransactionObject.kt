package com.example.spendingtracker.RealmObjects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class TransactionObject:RealmObject()
{
    @PrimaryKey
    @Required
    val id = 0

    @Required
    val trasactionName : String = ""

    @Required
    val transactionAmount  = 0

    @Required
    val trasactionDate = ""

    @Required
    val category: CategoryObject =  CategoryObject()
}