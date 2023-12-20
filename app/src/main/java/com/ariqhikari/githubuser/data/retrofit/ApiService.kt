package com.ariqhikari.githubuser.data.retrofit

import com.ariqhikari.githubuser.data.response.DetailUser
import com.ariqhikari.githubuser.data.response.GithubResponse
import com.ariqhikari.githubuser.data.response.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}