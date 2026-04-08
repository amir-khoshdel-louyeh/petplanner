package com.petplanner

object LocalDataRepository {
    private val pet = Pet(
        name = "Buddy",
        summary = "Golden Retriever · 3 yrs · Dog",
        personality = "Personality: playful, friendly, loves morning walks",
        mood = "Happy",
        weight = "72 lbs"
    )

    private val tasks = mutableListOf(
        Task(id = "feed", title = "Feed Buddy breakfast", completed = false),
        Task(id = "walk", title = "Morning walk and training", completed = true),
        Task(id = "medication", title = "Medication reminder: joint care", completed = false)
    )

    private val reminders = mutableListOf(
        Reminder(id = "reminder-1", title = "Morning walk", scheduledTime = "8:00 AM", completed = true),
        Reminder(id = "reminder-2", title = "Feed Buddy breakfast", scheduledTime = "12:00 PM", completed = false),
        Reminder(id = "reminder-3", title = "Medication check", scheduledTime = "6:00 PM", completed = false)
    )

    fun getPet(): Pet = pet

    fun getTasks(): List<Task> = tasks.toList()

    fun getReminders(): List<Reminder> = reminders.toList()

    fun toggleTaskCompleted(taskId: String): Task? {
        val index = tasks.indexOfFirst { it.id == taskId }
        if (index == -1) return null
        val task = tasks[index]
        val updated = task.copy(completed = !task.completed)
        tasks[index] = updated
        return updated
    }

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun completedTaskCount(): Int = tasks.count { it.completed }

    fun upcomingReminders(): List<Reminder> = reminders.filter { !it.completed }
}
