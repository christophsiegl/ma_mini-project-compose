package com.example.foodflix.network

import com.example.foodflix.model.Meal
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RecipesResponse {
    @Json(name = "meals")
    var meals: List<Meal> = listOf()
}