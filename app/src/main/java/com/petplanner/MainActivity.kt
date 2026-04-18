package com.petplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LocalDataRepository.initialize(this)

        if (OnboardingPreferences.isOnboardingComplete(this)) {
            showDashboard()
        } else {
            showOnboarding()
        }
    }

    fun completeOnboarding() {
        showDashboard()
    }

    private fun showDashboard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, DashboardFragment())
            .commit()
    }

    private fun showOnboarding() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, OnboardingFragment())
            .commit()
    }
}
