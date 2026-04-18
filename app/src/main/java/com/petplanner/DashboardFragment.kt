package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private lateinit var greetingText: TextView
    private lateinit var tasksRecycler: RecyclerView
    private lateinit var addTaskButton: Button
    private lateinit var taskAdapter: TaskAdapter

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
        tasksRecycler = view.findViewById(R.id.tasksRecycler)
        addTaskButton = view.findViewById(R.id.addTaskButton)
    }

    private fun loadDashboard() {
        viewLifecycleOwner.lifecycleScope.launch {
            val pet = LocalDataRepository.getPet()
            bindDashboardData(pet)
            val tasks = LocalDataRepository.getTasks()
            bindTaskList(tasks)
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

    private fun bindTaskList(tasks: List<Task>) {
        taskAdapter = TaskAdapter(tasks) { updatedTask ->
            viewLifecycleOwner.lifecycleScope.launch {
                LocalDataRepository.toggleTaskCompleted(updatedTask.id)
                Toast.makeText(requireContext(), "Task updated: ${updatedTask.title}", Toast.LENGTH_SHORT).show()
            }
        }
        tasksRecycler.layoutManager = LinearLayoutManager(requireContext())
        tasksRecycler.adapter = taskAdapter

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val updatedTask = taskAdapter.toggleTaskCompleted(viewHolder.bindingAdapterPosition)
                if (updatedTask != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        LocalDataRepository.toggleTaskCompleted(updatedTask.id)
                    }
                }
            }
        }

        ItemTouchHelper(swipeHandler).attachToRecyclerView(tasksRecycler)
        addTaskButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, TaskEditorFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
