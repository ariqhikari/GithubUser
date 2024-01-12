package com.ariqhikari.githubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.ariqhikari.githubuser.data.local.entity.FavoriteEntity
import com.ariqhikari.githubuser.data.local.room.FavoriteDao
import com.ariqhikari.githubuser.data.remote.response.DetailUser
import com.ariqhikari.githubuser.data.remote.response.User
import com.ariqhikari.githubuser.data.remote.retrofit.ApiService
import com.ariqhikari.githubuser.utils.AppExecutors

class UserRepository(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {
    private val _listUser = MutableLiveData<Result<List<User>>>()
    private val listUser: LiveData<Result<List<User>>> = _listUser

    private val _user = MutableLiveData<Result<DetailUser>>()
    val user: LiveData<Result<DetailUser>> = _user

    private val _listFollower = MutableLiveData<Result<List<User>>>()
    val listFollower: LiveData<Result<List<User>>> = _listFollower

    private val _listFollowing = MutableLiveData<Result<List<User>>>()
    val listFollowing: LiveData<Result<List<User>>> = _listFollowing

    fun getUsers(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUsers(username)
            _listUser.value = Result.Success(response.items)
            emitSource(listUser)
        } catch (e: Exception) {
            Log.d("UserRepository", "getUsers: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetail(username: String): LiveData<Result<DetailUser>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(username)
            _user.value = Result.Success(response)
            emitSource(user)
        } catch (e: Exception) {
            Log.d("UserRepository", "getDetail: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollower(username)
            _listFollower.value = Result.Success(response)
            emitSource(listFollower)
        } catch (e: Exception) {
            Log.d("UserRepository", "getFollowers: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowings(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowing(username)
            _listFollowing.value = Result.Success(response)
            emitSource(listFollowing)
        } catch (e: Exception) {
            Log.d("UserRepository", "getFollowings: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavorites(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getFavorites()
    }

    fun getFavorite(username: String): LiveData<FavoriteEntity> {
        return favoriteDao.getFavorite(username)
    }

    suspend fun insertFavorite(user: FavoriteEntity) {
        favoriteDao.insert(user)
    }

    suspend fun deleteFavorite(user: FavoriteEntity) {
        favoriteDao.delete(user)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, favoriteDao, appExecutors)
            }.also { instance = it }
    }
}