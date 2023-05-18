package com.example.foodflix.network

import com.example.foodflix.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val recipeListRetrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(getLoggerIntercepter())
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.RECIPE_LIST_BASE_URL)
    .build()

fun getLoggerIntercepter(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

interface RecipeAPIService {
    @GET("filter.php?a=Canadian")
    suspend fun getCanadianMeals(
    ): RecipesResponse

    @GET("search.php?f=a")
    suspend fun getMealByA(
    ): RecipeLookupResponse

    @GET("lookup.php?c={category}")
    suspend fun getMealsByCategory(
        @Path("category")
        category: String
    ): RecipesResponse

    //www.themealdb.com/api/json/v1/1/lookup.php?i=52772
    //52804
    @GET("lookup.php?i={movie_id}")
    suspend fun getMealById(
        @Path("movie_id")
        movieId: String,
    ): RecipeLookupResponse
}
object RecipeApi{
    val recipeListRetrofitService : RecipeAPIService by lazy {
        recipeListRetrofit.create(RecipeAPIService::class.java)
    }
}