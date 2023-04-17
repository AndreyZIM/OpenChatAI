package com.andreyzim.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MessageCache::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao() : MessageDao
}