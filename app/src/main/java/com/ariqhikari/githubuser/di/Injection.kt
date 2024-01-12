package com.ariqhikari.githubuser.di

import android.content.Context
import com.ariqhikari.githubuser.data.UserRepository
import com.ariqhikari.githubuser.data.local.room.FavoriteDatabase
import com.ariqhikari.githubuser.data.remote.retrofit.ApiConfig
import com.ariqhikari.githubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoritesDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiService, dao, appExecutors)
    }
}