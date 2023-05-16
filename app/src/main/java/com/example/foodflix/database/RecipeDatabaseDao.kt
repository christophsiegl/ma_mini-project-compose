package com.example.foodflix.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodflix.model.Meal

@Dao
interface RecipeDatabaseDao {
    @Insert
    suspend fun insert(meal: Meal)
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