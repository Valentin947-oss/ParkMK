package com.parkmk.ui.map

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.parkmk.R
import com.parkmk.databinding.ActivityMainBinding
import com.parkmk.ui.parking.ParkingTimerService
import com.parkmk.ui.auth.AuthActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // Проверува секои 2 секунди дали сервисот работи
    private val handler = Handler(Looper.getMainLooper())
    private val serviceWatcher = object : Runnable {
        override fun run() {
            updateActiveTab()
            handler.postDelayed(this, 2000L)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val lang = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
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

    override fun onResume() {
        super.onResume()
        val lang = getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = Locale(lang)
        if (resources.configuration.locales[0].language != locale.language) {
            recreate()
        }
        updateActiveTab()
        handler.post(serviceWatcher)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(serviceWatcher)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val lang = getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHost.navController

        binding.bottomNav?.setOnItemSelectedListener { item ->
            if (navController.currentDestination?.id == item.itemId) return@setOnItemSelectedListener true
            when (item.itemId) {
                R.id.mapFragment           -> { navController.navigate(R.id.mapFragment); true }
                R.id.activeParkingFragment -> { navController.navigate(R.id.activeParkingFragment); true }
                R.id.historyFragment       -> { navController.navigate(R.id.historyFragment); true }
                R.id.profileFragment       -> { navController.navigate(R.id.profileFragment); true }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, dest, _ ->
            val hideOn = setOf(
                R.id.receiptFragment,
                R.id.addVehicleMainFragment
            )
            binding.bottomNav?.visibility =
                if (dest.id in hideOn) View.GONE else View.VISIBLE

            val targetId = when (dest.id) {
                R.id.mapFragment           -> R.id.mapFragment
                R.id.activeParkingFragment -> R.id.activeParkingFragment
                R.id.historyFragment       -> R.id.historyFragment
                R.id.profileFragment       -> R.id.profileFragment
                else -> null
            }
            if (targetId != null && binding.bottomNav?.selectedItemId != targetId) {
                binding.bottomNav?.selectedItemId = targetId
            }
        }

        updateActiveTab()
    }

    // Го прикажува/крие табот за активна сесија
    private fun updateActiveTab() {
        val menu = binding.bottomNav?.menu ?: return
        val item = menu.findItem(R.id.activeParkingFragment) ?: return
        item.isVisible = ParkingTimerService.isRunning
    }

    fun goToLogin() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}