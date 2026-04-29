package com.petplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LocalDataRepository.initialize(this)

        // Temporarily disable onboarding setup flow and open dashboard directly.
        // We keep the original logic as comments so it can be restored later.
        // if (OnboardingPreferences.isOnboardingComplete(this)) {
        //     showDashboard()
        // } else {
        //     showOnboarding()
        // }
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

    private fun showOnboarding() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, OnboardingFragment())
            .commit()
    }
}
