package com.ariqhikari.githubuser.data.remote.retrofit

import com.ariqhikari.githubuser.data.remote.response.DetailUser
import com.ariqhikari.githubuser.data.remote.response.GithubResponse
import com.ariqhikari.githubuser.data.remote.response.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") username: String
    ): GithubResponse

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): DetailUser

    @GET("users/{username}/followers")
    suspend fun getUserFollower(
        @Path("username") username: String
    ): List<User>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): List<User>
}