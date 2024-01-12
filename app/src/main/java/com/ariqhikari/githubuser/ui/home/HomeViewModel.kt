package com.ariqhikari.githubuser.ui.home

import androidx.lifecycle.ViewModel
import com.ariqhikari.githubuser.data.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsers(username: String) = userRepository.getUsers(username)
}




