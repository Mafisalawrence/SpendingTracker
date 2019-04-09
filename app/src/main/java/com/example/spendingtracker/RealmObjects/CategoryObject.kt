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

    @Required
     var name: String = ""

    @Required
    var subCategory: RealmList<SubCategoryObject>? = null
}

