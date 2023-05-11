package com.ltu.m7019e.v23.themoviedb.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ltu.m7019e.v23.themoviedb.model.Movie

@Dao
interface MovieDatabaseDao {
    @Insert
    suspend fun insert(movie: Movie)
    @Insert
    suspend fun insertAll(movie: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * from movies ORDER BY  id ASC")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * from movies ORDER BY  id ASC")
    fun getAllMoviesAsLiveData(): LiveData<List<Movie>>

    @Query("SELECT EXISTS(SELECT * from movies WHERE id = :id)")
    suspend fun isFavourite(id: Long): Boolean
}