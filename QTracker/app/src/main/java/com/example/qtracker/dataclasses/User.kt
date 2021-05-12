package com.example.qtracker.dataclasses

data class User(
        val id: String,
        val name: String,
        val password: String,
        val group: String,
        val subgroup: String
){}