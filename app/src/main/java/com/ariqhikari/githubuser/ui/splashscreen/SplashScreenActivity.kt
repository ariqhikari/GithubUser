package com.ariqhikari.githubuser.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import com.ariqhikari.githubuser.R
import com.ariqhikari.githubuser.databinding.ActivityScreenSplashBinding
import com.ariqhikari.githubuser.ui.home.HomeActivity
import com.ariqhikari.githubuser.ui.setting.SettingPreferences
import com.ariqhikari.githubuser.ui.setting.dataStore

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenSplashBinding
    private val time: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        pref.getThemeSetting().asLiveData().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.ivGithub.setImageResource(R.drawable.github_white)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.ivGithub.setImageResource(R.drawable.github)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, time)
    }
}