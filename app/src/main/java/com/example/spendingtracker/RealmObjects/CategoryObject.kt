package com.example.spendingtracker.RealmObjects

import io.realm.RealmObject
import io.realm.RealmList
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required


open class CategoryObject : RealmObject()
{
     @Required
     @PrimaryKey
     var id: Long = 0
         get() = field
         set(value) { field = value }


    @Required
    var name: String = ""
        get() = field
        set(value) { field = value }

    // val subCategory: RealmList<SubCategoryObject>? = null



}

