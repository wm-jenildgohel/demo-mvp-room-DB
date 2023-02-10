package com.example.demomvpdb.database

import androidx.room.Insert
import androidx.room.Query
import com.example.demomvpdb.model.User

@androidx.room.Dao
interface UserDao {

    @Insert
    fun addUser(user: User)

    @Query("Select * From User WHERE email LIKE :email AND " + "password LIKE :password")
    fun isExists(email: String, password: String): Boolean

}