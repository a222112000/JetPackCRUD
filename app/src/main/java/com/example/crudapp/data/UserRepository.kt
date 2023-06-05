package com.example.crudapp.data

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: Users)


    suspend fun delete(user: Users)


    suspend fun getUserById(id: Int): Users?

    fun getUsers(): Flow<List<Users>>
}