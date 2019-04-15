package com.example.spendingtracker.RealmObjects

import io.realm.RealmObject
import io.realm.RealmList
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required


open class CategoryObject : RealmObject()
{
     @PrimaryKey
     var id: String = ""

    @Required
     var categoryName: String = ""

    var subCategory: RealmList<SubCategoryObject>? = null
}

