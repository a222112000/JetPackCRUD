package com.example.crudapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Users)

    @Delete
    suspend fun delete(user: Users)

    @Query("select * from  users where id = :id")
    suspend fun getUserById(id: Int): Users?

    @Query("select * from users")
    fun getUsers(): Flow<List<Users>>

}