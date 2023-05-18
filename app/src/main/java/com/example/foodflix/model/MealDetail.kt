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
    @PrimaryKey
    @Json(name = "idMeal")
    val idMeal: String,
    @ColumnInfo(name = "strMeal")
    @Json(name = "strMeal")
    val strMeal: String,
    @ColumnInfo(name = "strCategory")
    @Json(name = "strCategory")
    val strCategory: String,
    //@ColumnInfo(name = "strDrinkAlternate") //Attention: Null in every response!!!
    //@Json(name = "strDrinkAlternate")
    //val strDrinkAlternate: String ,
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
    @ColumnInfo(name = "strIngredient2")
    @Json(name = "strIngredient2")
    val strIngredient2: String,
    @ColumnInfo(name = "strIngredient3")
    @Json(name = "strIngredient3")
    val strIngredient3: String,
    @ColumnInfo(name = "strIngredient4")
    @Json(name = "strIngredient4")
    val strIngredient4: String,
    @ColumnInfo(name = "strIngredient5")
    @Json(name = "strIngredient5")
    val strIngredient5: String,
    @ColumnInfo(name = "strIngredient6")
    @Json(name = "strIngredient6")
    val strIngredient6: String,
    @ColumnInfo(name = "strIngredient7")
    @Json(name = "strIngredient7")
    val strIngredient7: String,
    @ColumnInfo(name = "strIngredient8")
    @Json(name = "strIngredient8")
    val strIngredient8: String,
    @ColumnInfo(name = "strMeasure1")
    @Json(name = "strMeasure1")
    val strMeasure1: String,
    @ColumnInfo(name = "strMeasure2")
    @Json(name = "strMeasure2")
    val strMeasure2: String,
    @ColumnInfo(name = "strMeasure3")
    @Json(name = "strMeasure3")
    val strMeasure3: String,
    @ColumnInfo(name = "strMeasure4")
    @Json(name = "strMeasure4")
    val strMeasure4: String,
    @ColumnInfo(name = "strMeasure5")
    @Json(name = "strMeasure5")
    val strMeasure5: String,
    @ColumnInfo(name = "strMeasure6")
    @Json(name = "strMeasure6")
    val strMeasure6: String,
    @ColumnInfo(name = "strMeasure7")
    @Json(name = "strMeasure7")
    val strMeasure7: String,
    @ColumnInfo(name = "strMeasure8")
    @Json(name = "strMeasure8")
    val strMeasure8: String,
) : Parcelable