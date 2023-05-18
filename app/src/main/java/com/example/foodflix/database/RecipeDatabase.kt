package com.example.foodflix.database

import android.content.Context
import androidx.room.*
import com.example.foodflix.model.Meal
import com.example.foodflix.model.MealDetail

@Database(entities = [Meal::class, MealDetail::class], version = 9, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDatabaseDao(): RecipeDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null
        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}