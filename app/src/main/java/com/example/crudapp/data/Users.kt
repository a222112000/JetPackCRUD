package com.example.crudapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name:String,
    val age: Int,
    val gender: String,
    val city: String,
    val userSelected: Boolean
)