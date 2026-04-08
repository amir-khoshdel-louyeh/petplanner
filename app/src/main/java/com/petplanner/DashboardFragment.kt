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
    private lateinit var petNameText: TextView
    private lateinit var petSummaryText: TextView
    private lateinit var petPersonalityText: TextView
    private lateinit var moodValueText: TextView
    private lateinit var weightValueText: TextView
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
        bindDashboardData(DashboardModel.samplePet())
        bindTaskList(DashboardModel.sampleTasks())
    }

    private fun bindViews(view: View) {
        petNameText = view.findViewById(R.id.petNameText)
        petSummaryText = view.findViewById(R.id.petSummaryText)
        petPersonalityText = view.findViewById(R.id.petPersonalityText)
        moodValueText = view.findViewById(R.id.moodValue)
        weightValueText = view.findViewById(R.id.weightValue)
        tasksRecycler = view.findViewById(R.id.tasksRecycler)
        addTaskButton = view.findViewById(R.id.addTaskButton)
    }

    private fun bindDashboardData(pet: Pet) {
        petNameText.text = pet.name
        petSummaryText.text = pet.summary
        petPersonalityText.text = pet.personality
        moodValueText.text = pet.mood
        weightValueText.text = pet.weight
    }

    private fun bindTaskList(tasks: List<Task>) {
        taskAdapter = TaskAdapter(tasks) { updatedTask ->
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
