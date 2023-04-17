package com.andreyzim.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface MessageDao {

    @Query("SELECT * FROM message_table ORDER BY id DESC")
    fun allMessages(): Flow<List<MessageCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageCache)

    @Query("DELETE FROM message_table")
    suspend fun clear()
}