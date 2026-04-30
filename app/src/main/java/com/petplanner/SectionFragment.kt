package com.petplanner

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class SectionFragment : Fragment(R.layout.fragment_section) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(ARG_SECTION_TITLE) ?: getString(R.string.nav_more)
        val sectionTitle = view.findViewById<TextView>(R.id.sectionTitle)
        val addTaskButton = view.findViewById<MaterialButton>(R.id.addTaskButton)

        if (title == getString(R.string.nav_todo)) {
            sectionTitle.text = getString(R.string.todo_section_title)
            addTaskButton.visibility = View.VISIBLE
            addTaskButton.text = getString(R.string.add_task_button)
        } else {
            sectionTitle.text = getString(R.string.section_title_template, title)
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
