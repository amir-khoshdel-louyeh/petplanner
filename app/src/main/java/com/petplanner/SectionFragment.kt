package com.petplanner

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class SectionFragment : Fragment(R.layout.fragment_section) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(ARG_SECTION_TITLE) ?: getString(R.string.nav_more)
        view.findViewById<TextView>(R.id.sectionTitle).text = getString(R.string.section_title_template, title)
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
