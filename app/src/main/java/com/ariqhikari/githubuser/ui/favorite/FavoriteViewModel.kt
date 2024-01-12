package com.ariqhikari.githubuser.ui.favorite

import androidx.lifecycle.ViewModel
import com.ariqhikari.githubuser.data.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFavorites() = userRepository.getFavorites()
}




