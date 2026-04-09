package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val languageValue = view.findViewById<TextView>(R.id.languageValue)
        val openReminderButton = view.findViewById<Button>(R.id.openReminderSettingsButton)

        languageValue.text = OnboardingPreferences.getLanguage(requireContext())

        openReminderButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, ReminderSettingsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
