package com.ariqhikari.githubuser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ariqhikari.githubuser.data.UserRepository
import com.ariqhikari.githubuser.data.local.entity.FavoriteEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    var favorite: Boolean = false

    fun getDetail(username: String) = userRepository.getDetail(username)

    fun getFollowers(username: String) = userRepository.getFollowers(username)

    fun getFollowings(username: String) = userRepository.getFollowings(username)

    fun getFavorite(username: String) = userRepository.getFavorite(username)

    fun insertFavorite(user: FavoriteEntity) {
        viewModelScope.launch {
            userRepository.insertFavorite(user)
        }
    }

    fun deleteFavorite(user: FavoriteEntity) {
        viewModelScope.launch {
            userRepository.deleteFavorite(user)
        }
    }
}




