package com.ariqhikari.githubuser.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ariqhikari.githubuser.R
import com.ariqhikari.githubuser.data.response.DetailUser
import com.ariqhikari.githubuser.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        getUser()
        setupPagerAdapter()
        setupSnackBar()
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

    private fun setupSnackBar() {
        detailViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getUser() {
        val username = intent.getStringExtra(EXTRA_DATA) as String
        detailViewModel.getDetail(username)
        detailViewModel.user.observe(this) { user ->
            setUser(user)
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
