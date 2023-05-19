package com.example.foodflix.network

import com.example.foodflix.model.MealDetail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RecipeLookupResponse {
    @Json(name = "meals")
    var meals : List<MealDetail> = listOf()
}