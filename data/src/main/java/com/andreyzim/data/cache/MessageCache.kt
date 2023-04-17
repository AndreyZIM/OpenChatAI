package com.andreyzim.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class MessageCache(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "received") val received: Boolean,
    @ColumnInfo(name = "created") val created: Long
)
