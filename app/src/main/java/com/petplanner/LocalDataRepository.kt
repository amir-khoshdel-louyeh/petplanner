package com.petplanner

import android.content.Context

object LocalDataRepository {
    private lateinit var database: AppDatabase

    fun initialize(context: Context) {
        if (!::database.isInitialized) {
            database = AppDatabase.getInstance(context)
        }
    }

    suspend fun getPet(): Pet? {
        return database.petDao().getPet()?.toPet()
    }

    suspend fun savePetProfile(pet: Pet) {
        database.petDao().insertPet(pet.toEntity())
    }

    suspend fun getTasks(): List<Task> {
        return database.taskDao().getAllTasks().map { it.toTask() }
    }

    suspend fun getReminders(): List<Reminder> {
        return database.reminderDao().getAllReminders().map { it.toReminder() }
    }

    suspend fun toggleTaskCompleted(taskId: String): Task? {
        val taskEntity = database.taskDao().getTaskById(taskId) ?: return null
        val updatedEntity = taskEntity.copy(completed = !taskEntity.completed)
        database.taskDao().updateTask(updatedEntity)
        return updatedEntity.toTask()
    }

    suspend fun addTask(task: Task) {
        database.taskDao().insertTask(task.toEntity())
    }

    suspend fun completedTaskCount(): Int {
        return database.taskDao().getCompletedCount()
    }

    suspend fun upcomingReminders(): List<Reminder> {
        return database.reminderDao().getUpcomingReminders().map { it.toReminder() }
    }

    suspend fun seedInitialDataIfNeeded() {
        if (database.taskDao().getAllTasks().isEmpty()) {
            database.taskDao().insertTask(Task(id = "feed", title = "Feed your pet breakfast", completed = false).toEntity())
            database.taskDao().insertTask(Task(id = "walk", title = "Morning walk and training", completed = true).toEntity())
            database.taskDao().insertTask(Task(id = "medication", title = "Medication reminder: joint care", completed = false).toEntity())
        }

        if (database.reminderDao().getAllReminders().isEmpty()) {
            database.reminderDao().insertReminder(Reminder(id = "reminder-1", title = "Morning walk", scheduledTime = "8:00 AM", completed = true).toEntity())
            database.reminderDao().insertReminder(Reminder(id = "reminder-2", title = "Feed your pet breakfast", scheduledTime = "12:00 PM", completed = false).toEntity())
            database.reminderDao().insertReminder(Reminder(id = "reminder-3", title = "Medication check", scheduledTime = "6:00 PM", completed = false).toEntity())
        }
    }
}
