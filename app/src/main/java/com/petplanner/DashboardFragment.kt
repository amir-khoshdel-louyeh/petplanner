package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardFragment : Fragment() {
    private lateinit var greetingText: TextView
    private lateinit var greetingSubtitle: TextView
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
        bindDashboardData(LocalDataRepository.getPet())
        bindTaskList(LocalDataRepository.getTasks())
    }

    private fun bindViews(view: View) {
        greetingText = view.findViewById(R.id.greetingText)
        greetingSubtitle = view.findViewById(R.id.greetingSubtitle)
        tasksRecycler = view.findViewById(R.id.tasksRecycler)
        addTaskButton = view.findViewById(R.id.addTaskButton)
    }

    private fun bindDashboardData(pet: Pet) {
        greetingText.text = "Hello, ${pet.name}!"
        greetingSubtitle.text = pet.summary
    }

    private fun bindTaskList(tasks: List<Task>) {
        taskAdapter = TaskAdapter(tasks) { updatedTask ->
            LocalDataRepository.toggleTaskCompleted(updatedTask.id)
            Toast.makeText(requireContext(), "Task updated: ${updatedTask.title}", Toast.LENGTH_SHORT).show()
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
                taskAdapter.toggleTaskCompleted(viewHolder.bindingAdapterPosition)
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
