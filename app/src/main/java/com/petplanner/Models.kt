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

data class Reminder(
    val id: String,
    val title: String,
    val scheduledTime: String,
    val completed: Boolean = false
)
