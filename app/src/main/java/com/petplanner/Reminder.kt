package com.petplanner

data class Reminder(
    val id: String,
    val title: String,
    val scheduledTime: String,
    val completed: Boolean = false
)
