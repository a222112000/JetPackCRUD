package com.example.crudapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crudapp.data.UserDatabase
import com.example.crudapp.data.UserRepoImpl
import com.example.crudapp.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUsersDatabase(app: Application):UserDatabase{
       return Room.databaseBuilder(
           app,
           UserDatabase::class.java,
           "userDb"
       ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(db:UserDatabase): UserRepository{
        return UserRepoImpl(db.dao)
    }
}