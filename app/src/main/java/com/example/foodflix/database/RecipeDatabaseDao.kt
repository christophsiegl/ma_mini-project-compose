package com.example.foodflix.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodflix.model.Meal
import com.example.foodflix.model.MealDetail

@Dao
interface RecipeDatabaseDao {
    @Insert(entity = Meal::class)
    suspend fun insert(meal: Meal)

    @Insert(entity = MealDetail::class)
    suspend fun insert(mealDetail: MealDetail)

    // Maybe we need this later, im not sure.
    //@Query("UPDATE recipes SET mealDetail =  :mealDetail WHERE idMeal = :id")
    //suspend fun insertDetails(mealDetail: MealDetail, id: Long)

    @Insert
    suspend fun insertAll(meal: List<Meal>)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

    @Query("SELECT * from recipes ORDER BY idMeal ASC")
    suspend fun getAllRecipes(): List<Meal>

    @Query("SELECT * from recipes ORDER BY idMeal ASC")
    fun getAllRecipesAsLiveData(): LiveData<List<Meal>>

    @Query("SELECT EXISTS(SELECT * from recipes WHERE idMeal = :id)")
    suspend fun isFavourite(id: Long): Boolean
}