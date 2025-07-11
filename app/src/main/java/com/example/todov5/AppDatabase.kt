package com.example.todov5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // just a basic singleton thing
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
