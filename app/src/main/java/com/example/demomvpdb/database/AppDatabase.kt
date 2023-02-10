package com.example.demomvpdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demomvpdb.model.User

@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "course_database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                    .also { instance = it }
            }
            return instance
        }
    }

}