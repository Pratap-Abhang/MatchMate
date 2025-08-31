package com.abhang.matchmate.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhang.matchmate.data.local.dao.UserDao
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.utils.Constants


@Database(entities = [UserData::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract fun getUserDao(): UserDao

    companion object {
        private const val DB_NAME = Constants.DATABASE.DB_NAME

        @Volatile
        private var instance: UserDatabase?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

}