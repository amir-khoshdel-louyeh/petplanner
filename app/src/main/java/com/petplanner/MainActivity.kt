package com.petplanner

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottomNavigation)
        LocalDataRepository.initialize(this)

        if (OnboardingPreferences.isOnboardingComplete(this)) {
            showAppContent()
        } else {
            showOnboarding()
        }
    }

    fun completeOnboarding() {
        showAppContent()
    }

    private fun showAppContent() {
        bottomNav.visibility = View.VISIBLE
        setupBottomNavigation()
    }

    private fun showOnboarding() {
        bottomNav.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, OnboardingFragment())
            .commit()
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_dashboard -> DashboardFragment()
                R.id.navigation_profile -> PetProfileFragment()
                R.id.navigation_health -> HealthMoodFragment()
                R.id.navigation_calendar -> CalendarFragment()
                R.id.navigation_settings -> SettingsFragment()
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
