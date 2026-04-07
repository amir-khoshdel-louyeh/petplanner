package com.petplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_dashboard -> DashboardFragment()
                R.id.navigation_profile -> PlaceholderFragment.newInstance(getString(R.string.pet_profile_label))
                R.id.navigation_health -> PlaceholderFragment.newInstance(getString(R.string.health_label))
                R.id.navigation_calendar -> PlaceholderFragment.newInstance(getString(R.string.calendar_label))
                R.id.navigation_settings -> PlaceholderFragment.newInstance(getString(R.string.settings_label))
                else -> DashboardFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, fragment)
                .commit()
            true
        }
        bottomNav.selectedItemId = R.id.navigation_dashboard
    }
}
