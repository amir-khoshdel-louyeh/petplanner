package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private lateinit var greetingText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        loadDashboard()
    }

    override fun onResume() {
        super.onResume()
        loadDashboard()
    }

    private fun bindViews(view: View) {
        greetingText = view.findViewById(R.id.greetingText)
    }

    private fun loadDashboard() {
        viewLifecycleOwner.lifecycleScope.launch {
            val pet = LocalDataRepository.getPet()
            bindDashboardData(pet)
        }
    }

    private fun bindDashboardData(pet: Pet?) {
        val userName = OnboardingPreferences.getUserName(requireContext())
        if (userName.isNullOrBlank()) {
            greetingText.text = getString(R.string.home_title)
        } else {
            greetingText.text = "Hello $userName"
        }
    }

}
