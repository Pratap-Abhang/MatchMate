package com.abhang.matchmate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.utils.Constants

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(userData: UserData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(userData: List<UserData>)

    @Query("SELECT * FROM "+ Constants.DATABASE.DB_TABLE_NAME + " ORDER BY userId ASC")
    suspend fun getAllData(): List<UserData>

    @Query("SELECT * FROM "+ Constants.DATABASE.DB_TABLE_NAME + " ORDER BY userId ASC LIMIT :limit OFFSET :offset")
    suspend fun getData(limit: Int, offset:Int): List<UserData>

    @Query("SELECT userId FROM "+ Constants.DATABASE.DB_TABLE_NAME + " WHERE userId IN (:id)")
    suspend fun checkIfUserExist(id: String): String
}