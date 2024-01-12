package com.ariqhikari.githubuser.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.ariqhikari.githubuser.R
import com.ariqhikari.githubuser.data.Result
import com.ariqhikari.githubuser.data.local.entity.FavoriteEntity
import com.ariqhikari.githubuser.data.remote.response.DetailUser
import com.ariqhikari.githubuser.databinding.ActivityDetailBinding
import com.ariqhikari.githubuser.ui.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        binding.ibBack.setOnClickListener { onBackPressed() }

        getUser()
        setupPagerAdapter()
    }

    private fun setupPagerAdapter() {
        val detailPagerAdapter = DetailPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = detailPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getUser() {
        val username = intent.getStringExtra(EXTRA_DATA) as String
        detailViewModel.getDetail(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
//                        showLoading(true)
                    }

                    is Result.Success -> {
//                        showLoading(false)
                        val user = result.data
                        setUser(user)
                        getFavorite(FavoriteEntity(user.login!!, user.avatarUrl!!, user.htmlUrl!!))
                    }

                    is Result.Error -> {
//                         showLoading(false)
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

    private fun getFavorite(user: FavoriteEntity) {
        detailViewModel.getFavorite(user.username).observe(this) { result ->
            if (result != null) {
                detailViewModel.favorite = true
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_favorite
                    )
                )
            } else {
                detailViewModel.favorite = false
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_favorite_border
                    )
                )
            }

            addOrDeleteFavorite(user)
        }
    }

    private fun addOrDeleteFavorite(user: FavoriteEntity) {
        binding.fabFavorite.setOnClickListener {
            if (!detailViewModel.favorite) {
                detailViewModel.insertFavorite(user)

                Snackbar.make(
                    window.decorView.rootView,
                    resources.getString(R.string.toast_save),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                detailViewModel.deleteFavorite(user)

                Snackbar.make(
                    window.decorView.rootView,
                    resources.getString(R.string.toast_delete),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun setUser(user: DetailUser) {
        user.apply {
            binding.tvName.text = name
            binding.tvUsername.text = login
            binding.tvCompany.text = company
            binding.tvLocation.text = location
            binding.tvRepo.text = publicRepos.toString()
            binding.tvFollowers.text = followers.toString()
            binding.tvFollowing.text = following.toString()
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_account))
                .into(binding.cvAvatar)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}
