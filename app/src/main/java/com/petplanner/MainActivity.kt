package com.petplanner

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var petNameText: TextView
    private lateinit var petSummaryText: TextView
    private lateinit var petPersonalityText: TextView
    private lateinit var moodValueText: TextView
    private lateinit var weightValueText: TextView
    private lateinit var tasksRecycler: RecyclerView
    private lateinit var addTaskButton: Button
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        bindDashboardData(DashboardModel.samplePet())
        bindTaskList(DashboardModel.sampleTasks())
    }

    private fun bindViews() {
        petNameText = findViewById(R.id.petNameText)
        petSummaryText = findViewById(R.id.petSummaryText)
        petPersonalityText = findViewById(R.id.petPersonalityText)
        moodValueText = findViewById(R.id.moodValue)
        weightValueText = findViewById(R.id.weightValue)
        tasksRecycler = findViewById(R.id.tasksRecycler)
        addTaskButton = findViewById(R.id.addTaskButton)
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
            Toast.makeText(this, "Task updated: ${updatedTask.title}", Toast.LENGTH_SHORT).show()
        }
        tasksRecycler.layoutManager = LinearLayoutManager(this)
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

        addTaskButton.setOnClickListener { showAddTaskDialog() }
    }

    private fun showAddTaskDialog() {
        val taskNameInput = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = "New task title"
        }

        AlertDialog.Builder(this)
            .setTitle("Add task")
            .setView(taskNameInput)
            .setPositiveButton("Add") { _, _ ->
                val taskTitle = taskNameInput.text.toString().trim()
                if (taskTitle.isEmpty()) {
                    Toast.makeText(this, "Please enter a task title.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val task = Task(
                    id = "task-${System.currentTimeMillis()}",
                    title = taskTitle,
                    completed = false
                )
                taskAdapter.addTask(task)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
