package com.petplanner

data class Pet(
    val name: String,
    val breed: String,
    val age: String,
    val summary: String,
    val personality: String,
    val mood: String,
    val weight: String
)

data class Task(
    val id: String,
    val title: String,
    val completed: Boolean
)

object DashboardModel {
    fun samplePet(): Pet = Pet(
        name = "Buddy",
        breed = "Golden Retriever",
        age = "3 yrs",
        summary = "Golden Retriever · 3 yrs · Dog",
        personality = "Personality: playful, friendly, loves morning walks",
        mood = "Happy",
        weight = "72 lbs"
    )

    fun sampleTasks(): List<Task> = listOf(
        Task(id = "feed", title = "Feed Buddy breakfast", completed = false),
        Task(id = "walk", title = "Morning walk and training", completed = true),
        Task(id = "medication", title = "Medication reminder: joint care", completed = false)
    )
}
