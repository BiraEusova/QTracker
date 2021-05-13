package com.example.qtracker.dataclasses

data class Lesson(
    val id: String,
    val name: String,
    val startTime: String,
    val finishTime: String,
    val teacher: String,
    val group: String,
    val color: String
)
