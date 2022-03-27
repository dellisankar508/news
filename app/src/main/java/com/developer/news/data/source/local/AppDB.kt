package com.developer.news.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_NAME = "news.db"

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            synchronized(this) {
                var ins = instance
                if (ins == null) {
                    ins = Room.databaseBuilder(context, AppDB::class.java, DB_NAME).build()
                    instance = ins
                }
                return ins
            }
        }
    }
}