package com.example.crudapp.data

import kotlinx.coroutines.flow.Flow

class UserRepoImpl(private val dao: UserDao): UserRepository {
    override suspend fun insertUser(user: Users) {
        dao.insertUser(user)
    }

    override suspend fun delete(user: Users) {
        dao.delete(user)
    }

    override suspend fun getUserById(id: Int): Users? {
       return dao.getUserById(id)
    }

    override fun getUsers(): Flow<List<Users>> {
       return dao.getUsers()
    }
}