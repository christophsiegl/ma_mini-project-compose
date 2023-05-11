package com.example.foodflix.model

data class Meal(
    val mealName: String,
    val mealID: String,
    val ingredients: MutableList<String>
)