package com.ariqhikari.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ariqhikari.githubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE username = :username")
    fun getFavorite(username: String): LiveData<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: FavoriteEntity)

    @Delete
    suspend fun delete(user: FavoriteEntity)
}