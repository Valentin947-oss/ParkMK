package com.parkmk.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.parkmk.R
import com.parkmk.data.repository.FirebaseRepository
import com.parkmk.ui.map.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.content.Context

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val lang = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = android.content.res.Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Примени јазик на почеток
        val lang = getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val repo = FirebaseRepository()
        lifecycleScope.launch {
            delay(1500L)
            val intent = if (repo.isLoggedIn) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, AuthActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}
