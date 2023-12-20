package com.ariqhikari.githubuser.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariqhikari.githubuser.data.response.User
import com.ariqhikari.githubuser.databinding.ActivityHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "GitHub User"
        homeViewModel.getDataSearch("ariq")

        setupSearchBar()
        setupUsers()
        setupSnackBar()
    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    homeViewModel.getDataSearch(searchView.text.toString())
                    false
                }
        }
    }

    private fun setupUsers() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        homeViewModel.listUser.observe(this) { users ->
            setUsersData(users)
        }
        homeViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupSnackBar() {
        homeViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUsersData(users: List<User>) {
        val adapter = HomeAdapter()
        adapter.submitList(users)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}