package com.example.foodflix.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipeDetail")
data class MealDetail(
    @PrimaryKey()
    @Json(name = "idMeal")
    val idMeal: String,
    @ColumnInfo(name = "strMeal")
    @Json(name = "strMeal")
    val strMeal: String,
    @ColumnInfo(name = "strDrinkAlternate")
    @Json(name = "strDrinkAlternate")
    val strDrinkAlternate: String,
    @ColumnInfo(name = "strCategory")
    @Json(name = "strCategory")
    val strCategory: String,
    @ColumnInfo(name = "strArea")
    @Json(name = "strArea")
    val strArea: String,
    @ColumnInfo(name = "strInstructions")
    @Json(name = "strInstructions")
    val strInstructions: String,
    @ColumnInfo(name = "strMealThumb")
    @Json(name = "strMealThumb")
    val strMealThumb: String,
    @ColumnInfo(name = "strTags")
    @Json(name = "strTags")
    val strTags: String,
    @ColumnInfo(name = "strIngredient1")
    @Json(name = "strIngredient1")
    val strIngredient1: String,
    @ColumnInfo(name = "strMeasure1")
    @Json(name = "strMeasure1")
    val strMeasure1: String,
) : Parcelable