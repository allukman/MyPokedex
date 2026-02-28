package com.karsatech.mypokedex.core.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun registerUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): UserEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    suspend fun isEmailExist(email: String): Boolean

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<UserEntity?>
}