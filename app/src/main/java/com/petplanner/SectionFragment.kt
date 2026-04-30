package com.petplanner

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class SectionFragment : Fragment(R.layout.fragment_section) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(ARG_SECTION_TITLE) ?: getString(R.string.nav_more)
        val sectionTitle = view.findViewById<TextView>(R.id.sectionTitle)
        val sectionIcon = view.findViewById<ImageView>(R.id.sectionIcon)
        val addTaskButton = view.findViewById<MaterialButton>(R.id.addTaskButton)

        sectionTitle.text = if (title == getString(R.string.nav_todo)) {
            getString(R.string.todo_section_title)
        } else {
            title
        }

        val iconRes = when (title) {
            getString(R.string.nav_todo) -> android.R.drawable.ic_menu_agenda
            getString(R.string.nav_health) -> R.drawable.ic_health_heart
            getString(R.string.nav_logs) -> android.R.drawable.ic_menu_recent_history
            getString(R.string.nav_more) -> android.R.drawable.ic_menu_more
            else -> android.R.drawable.ic_menu_help
        }

        sectionIcon.setImageResource(iconRes)
        sectionIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.primary))

        if (title == getString(R.string.nav_todo)) {
            addTaskButton.visibility = View.VISIBLE
        } else {
            addTaskButton.visibility = View.GONE
        }
    }

    companion object {
        private const val ARG_SECTION_TITLE = "arg_section_title"

        fun newInstance(title: String) = SectionFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_SECTION_TITLE, title)
            }
        }
    }
}
