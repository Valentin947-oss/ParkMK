package com.parkmk.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.parkmk.R
import com.parkmk.databinding.ActivityAuthBinding
import com.parkmk.ui.map.MainActivity
import android.content.Context

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
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
        // Примени јазик
        val lang = getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)

        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onResume() {
        super.onResume()
        val lang = getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = java.util.Locale(lang)
        if (resources.configuration.locales[0].language != locale.language) {
            recreate()
        }
    }
    fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
