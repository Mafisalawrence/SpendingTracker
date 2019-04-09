package com.example.spendingtracker.RealmObjects

import io.realm.RealmObject
import io.realm.annotations.Required

open class SubCategoryObject: RealmObject()
{
    @Required
    var subCategoryName: String = ""
}