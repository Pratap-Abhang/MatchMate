package com.abhang.matchmate.di

import android.content.Context
import com.abhang.matchmate.data.local.dao.UserDao
import com.abhang.matchmate.data.local.database.UserDatabase
import com.abhang.matchmate.data.local.repository.UserDataLocalRepositoryImpl
import com.abhang.matchmate.domain.repository.UserDataLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserLocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): UserDatabase =  UserDatabase(context)

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase): UserDao = database.getUserDao()

    @Provides
    @Singleton
    fun provideUserDataLocalRepository(dao: UserDao): UserDataLocalRepository {
        return UserDataLocalRepositoryImpl(dao)
    }
}