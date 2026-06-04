package com.parkmk.ui.map

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.parkmk.R
import com.parkmk.databinding.ActivityMainBinding
import com.parkmk.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHost.navController

        binding.bottomNav?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            val hideOn = setOf(R.id.activeParkingFragment, R.id.receiptFragment, R.id.addVehicleMainFragment)
            binding.bottomNav?.visibility = if (dest.id in hideOn) View.GONE else View.VISIBLE
        }
        // Примени зачуван јазик
        val lang = getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "mk") ?: "mk"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun goToLogin() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}
