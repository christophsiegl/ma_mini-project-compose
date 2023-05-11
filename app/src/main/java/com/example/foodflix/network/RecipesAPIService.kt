package com.example.foodflix.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesAPIService {

    @GET("lookup.php?i={meal_id}")
    suspend fun getDetailsById(
        @Path("meal_id")
        meal_id: String
    ): RecipeLookupResponse


}