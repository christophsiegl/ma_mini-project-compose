package com.example.foodflix.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodflix.model.Meal
import com.example.foodflix.model.MealDetail
import com.example.foodflix.model.UserData

@Dao
interface RecipeDatabaseDao {
    @Insert(entity = Meal::class)
    suspend fun insert(meal: Meal)

    @Insert(entity = MealDetail::class)
    suspend fun insert(mealDetail: MealDetail)

    @Insert(entity = UserData::class)
    suspend fun insert(userData: UserData)

    @Query("UPDATE UserData SET age =  :age WHERE email = :email")
    suspend fun update(email:String,age:Int)

    @Query("SELECT EXISTS(SELECT age from UserData WHERE email = :email)")
    suspend fun getAge(email:String) : Int

    @Query("UPDATE UserData SET favouriteRecipesIds = :idList WHERE email = :email")
    suspend fun updateFavouriteIds(email: String, idList: String)

    @Query("SELECT favouriteRecipesIds from UserData WHERE email = :email")
    suspend fun getFavouriteMealIds(email: String) : String

    // Maybe we need this later, im not sure.
    //@Query("UPDATE recipes SET mealDetail =  :mealDetail WHERE idMeal = :id")
    //suspend fun insertDetails(mealDetail: MealDetail, id: Long)

    @Insert(entity = Meal::class)
    suspend fun insertAll(meal: List<Meal>)

    @Delete(entity = Meal::class)
    suspend fun delete(meal: Meal)

    @Query("DELETE FROM recipeDetail")
    suspend fun deleteAllRecipeDetails()

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

    @Query("SELECT * from recipes ORDER BY idMeal ASC")
    suspend fun getAllRecipes(): List<Meal>

    @Query("SELECT * from UserData ORDER BY email ASC")
    suspend fun getAllUsers(): List<UserData>

    @Query("SELECT * from recipes ORDER BY idMeal ASC")
    fun getAllRecipesAsLiveData(): LiveData<List<Meal>>

    @Query("SELECT * FROM recipeDetail")
    fun getMealDetail(): LiveData<MealDetail>

    @Query("SELECT EXISTS(SELECT * from recipes WHERE idMeal = :id)")
    suspend fun isFavourite(id: Long): Boolean

    @Query("SELECT * from recipeDetail")
    fun getAllRecipeDetailsAsLiveData(): LiveData<List<MealDetail>>
}