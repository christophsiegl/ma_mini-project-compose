package com.example.foodflix.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
@Parcelize
@Entity(tableName = "recipes")
data class Meal(
    @PrimaryKey()
    @Json(name = "idMeal")
    val idMeal: String,
    @ColumnInfo(name = "strMeal")
    @Json(name = "strMeal")
    val strMeal: String,
    @ColumnInfo(name = "strMealThumb")
    @Json(name = "strMealThumb")
    val strMealThumb: String,

    @ColumnInfo(name = "mealDetail")
    val mealDetail: MealDetail

) : Parcelable