package com.example.myapplicationbdjobs.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplicationbdjobs.api.models.app_model.AppTable

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmark(appTable: AppTable)

    @Query("SELECT * FROM appTable")
    suspend fun getAllScheduleApps(): List<AppTable>

    @Query("SELECT EXISTS(SELECT * FROM appTable WHERE id = :id)")
    suspend fun checkIfExit(id:Int): Boolean

}
