package com.example.foodflix.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "UserData")
data class UserData(
    @PrimaryKey()
    val email: String,

    @ColumnInfo(name = "Age")
    val age: Int,

    //@ColumnInfo(name = "FavoriteCategory")
    //val favoriteCategory: String

) : Parcelable