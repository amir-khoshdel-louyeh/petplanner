package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class CalendarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val containerLayout = view.findViewById<LinearLayout>(R.id.remindersContainer)
        viewLifecycleOwner.lifecycleScope.launch {
            val reminders = LocalDataRepository.upcomingReminders()
            if (reminders.isEmpty()) {
                val emptyText = TextView(requireContext()).apply {
                    text = "No upcoming reminders"
                    setTextColor(resources.getColor(R.color.onBackgroundSecondary, requireContext().theme))
                    textSize = 16f
                }
                containerLayout.addView(emptyText)
                return@launch
            }

            reminders.forEach { reminder ->
                val reminderText = TextView(requireContext()).apply {
                    text = "${reminder.title} • ${reminder.scheduledTime}"
                    setTextColor(resources.getColor(R.color.onBackground, requireContext().theme))
                    textSize = 18f
                    setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.spacing_small))
                }
                containerLayout.addView(reminderText)
            }
        }
    }
}
