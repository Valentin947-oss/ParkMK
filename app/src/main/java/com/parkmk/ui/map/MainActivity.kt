package com.parkmk.ui.map

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.parkmk.R
import com.parkmk.databinding.ActivityMainBinding
import com.parkmk.ui.auth.AuthActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun attachBaseContext(newBase: Context) {
        val lang = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = android.content.res.Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val lang = context.getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val lang = getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        newConfig.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(newConfig, resources.displayMetrics)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHost.navController

        binding.bottomNav?.setOnItemSelectedListener { item ->
            if (navController.currentDestination?.id == item.itemId) return@setOnItemSelectedListener true
            when (item.itemId) {
                R.id.mapFragment     -> { navController.navigate(R.id.mapFragment); true }
                R.id.historyFragment -> { navController.navigate(R.id.historyFragment); true }
                R.id.profileFragment -> { navController.navigate(R.id.profileFragment); true }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, dest, _ ->
            val hideOn = setOf(
                R.id.activeParkingFragment,
                R.id.receiptFragment,
                R.id.addVehicleMainFragment
            )
            binding.bottomNav?.visibility =
                if (dest.id in hideOn) View.GONE else View.VISIBLE

            val targetId = when (dest.id) {
                R.id.mapFragment     -> R.id.mapFragment
                R.id.historyFragment -> R.id.historyFragment
                R.id.profileFragment -> R.id.profileFragment
                else -> null
            }
            if (targetId != null && binding.bottomNav?.selectedItemId != targetId) {
                binding.bottomNav?.selectedItemId = targetId
            }
        }
    }

    fun goToLogin() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}