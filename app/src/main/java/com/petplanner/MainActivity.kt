package com.petplanner

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var petNameText: TextView
    private lateinit var petSummaryText: TextView
    private lateinit var petPersonalityText: TextView
    private lateinit var moodValueText: TextView
    private lateinit var weightValueText: TextView
    private lateinit var taskFeedCheck: CheckBox
    private lateinit var taskWalkCheck: CheckBox
    private lateinit var taskHealthCheck: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        bindDashboardData(
            pet = DashboardModel.samplePet(),
            tasks = DashboardModel.sampleTasks()
        )
    }

    private fun bindViews() {
        petNameText = findViewById(R.id.petNameText)
        petSummaryText = findViewById(R.id.petSummaryText)
        petPersonalityText = findViewById(R.id.petPersonalityText)
        moodValueText = findViewById(R.id.moodValue)
        weightValueText = findViewById(R.id.weightValue)
        taskFeedCheck = findViewById(R.id.taskFeedCheck)
        taskWalkCheck = findViewById(R.id.taskWalkCheck)
        taskHealthCheck = findViewById(R.id.taskHealthCheck)
    }

    private fun bindDashboardData(pet: Pet, tasks: List<Task>) {
        petNameText.text = pet.name
        petSummaryText.text = pet.summary
        petPersonalityText.text = pet.personality
        moodValueText.text = pet.mood
        weightValueText.text = pet.weight

        tasks.forEach { task ->
            when (task.id) {
                "feed" -> bindTask(task, taskFeedCheck)
                "walk" -> bindTask(task, taskWalkCheck)
                "medication" -> bindTask(task, taskHealthCheck)
            }
        }
    }

    private fun bindTask(task: Task, checkbox: CheckBox) {
        checkbox.text = task.title
        checkbox.isChecked = task.completed
    }
}
