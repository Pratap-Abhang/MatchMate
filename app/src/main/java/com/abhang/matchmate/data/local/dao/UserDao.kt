package com.abhang.matchmate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.utils.Constants

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(userData: UserData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(userData: List<UserData>)

    @Query("SELECT * FROM "+ Constants.DATABASE.DB_TABLE_NAME + " ORDER BY userId DESC")
    suspend fun getAllData(): List<UserData>

    @Query("SELECT * FROM "+ Constants.DATABASE.DB_TABLE_NAME + " ORDER BY userId DESC LIMIT :limit OFFSET :offset")
    suspend fun getData(limit: Int, offset:Int): List<UserData>

    @Query("SELECT userId FROM "+ Constants.DATABASE.DB_TABLE_NAME + " WHERE userId IN (:id)")
    suspend fun checkIfUserExist(id: List<String>): List<String>

    @Query("UPDATE " + Constants.DATABASE.DB_TABLE_NAME + " SET status = :newstatus WHERE userId = :id")
    suspend fun updateStatus(id: String, newstatus: String): Int

    @Query("SELECT * FROM "+ Constants.DATABASE.DB_TABLE_NAME + " WHERE status = :newstatus ORDER BY userId DESC LIMIT :limit OFFSET :offset")
    suspend fun getUserBaseOnStatus(newstatus: String, limit: Int, offset:Int): List<UserData>
}