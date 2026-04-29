package com.petplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LocalDataRepository.initialize(this)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    showDashboard()
                    true
                }
                R.id.navigation_todo -> {
                    showSection(getString(R.string.nav_todo))
                    true
                }
                R.id.navigation_health -> {
                    showSection(getString(R.string.nav_health))
                    true
                }
                R.id.navigation_logs -> {
                    showSection(getString(R.string.nav_logs))
                    true
                }
                R.id.navigation_more -> {
                    showSection(getString(R.string.nav_more))
                    true
                }
                else -> false
            }
        }

        bottomNavigation.selectedItemId = R.id.navigation_dashboard
        showDashboard()
    }

    fun completeOnboarding() {
        showDashboard()
    }

    private fun showDashboard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, DashboardFragment())
            .commit()
    }

    private fun showSection(title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, SectionFragment.newInstance(title))
            .commit()
    }

    private fun showOnboarding() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, OnboardingFragment())
            .commit()
    }
}
