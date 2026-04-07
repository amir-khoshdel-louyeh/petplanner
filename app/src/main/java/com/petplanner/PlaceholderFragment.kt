package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class PlaceholderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_placeholder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val placeholderText = view.findViewById<android.widget.TextView>(R.id.placeholderText)
        placeholderText.text = arguments?.getString(ARG_TEXT) ?: ""
    }

    companion object {
        private const val ARG_TEXT = "arg_text"

        fun newInstance(text: String): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TEXT, text)
                }
            }
        }
    }
}
