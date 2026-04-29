package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private lateinit var greetingText: TextView
    private lateinit var todoTabLayout: TabLayout
    private lateinit var upcomingContent: LinearLayout
    private lateinit var completedContent: LinearLayout

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
        setupTodoTabs()
        loadDashboard()
    }

    override fun onResume() {
        super.onResume()
        loadDashboard()
    }

    private fun bindViews(view: View) {
        greetingText = view.findViewById(R.id.greetingText)
        todoTabLayout = view.findViewById(R.id.todoTabLayout)
        upcomingContent = view.findViewById(R.id.upcomingContent)
        completedContent = view.findViewById(R.id.completedContent)
    }

    private fun setupTodoTabs() {
        todoTabLayout.addTab(todoTabLayout.newTab().setText(R.string.todo_upcoming))
        todoTabLayout.addTab(todoTabLayout.newTab().setText(R.string.todo_completed))
        todoTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> showTodoSection(upcomingVisible = true)
                    1 -> showTodoSection(upcomingVisible = false)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) = Unit
            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })
        showTodoSection(upcomingVisible = true)
    }

    private fun showTodoSection(upcomingVisible: Boolean) {
        upcomingContent.visibility = if (upcomingVisible) View.VISIBLE else View.GONE
        completedContent.visibility = if (upcomingVisible) View.GONE else View.VISIBLE
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
