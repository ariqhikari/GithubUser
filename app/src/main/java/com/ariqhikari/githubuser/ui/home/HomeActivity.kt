package com.ariqhikari.githubuser.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariqhikari.githubuser.R
import com.ariqhikari.githubuser.databinding.ActivityHomeBinding
import com.ariqhikari.githubuser.ui.ViewModelFactory
import com.ariqhikari.githubuser.data.Result
import com.ariqhikari.githubuser.ui.favorite.FavoriteActivity
import com.ariqhikari.githubuser.ui.setting.SettingActivity
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        setupMenu()
        setupSearchBar()
        setupUsers("ariq")
    }

    private fun setupMenu() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    setupUsers(searchView.text.toString())
                    false
                }
        }
    }

    private fun setupUsers(username: String) {
        val adapter = HomeAdapter()
        binding.rvUser.adapter = adapter

        homeViewModel.getUsers(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        adapter.submitList(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Snackbar.make(
                            window.decorView.rootView,
                            "Terjadi kesalahan" + result.error,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}