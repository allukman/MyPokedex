package com.karsatech.mypokedex.core.data.repository

import com.karsatech.mypokedex.core.common.base.BaseRepository
import com.karsatech.mypokedex.core.data.source.local.db.PokedexDatabase
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val db: PokedexDatabase
) : PokedexRepository, BaseRepository() {
    override suspend fun registerUser(user: UserEntity) = db.userDao().registerUser(user)

    override suspend fun loginUser(email: String, password: String): UserEntity? =
        db.userDao().loginUser(email, password)

    override suspend fun isEmailExist(email: String) = db.userDao().isEmailExist(email)

    override fun getUser() = db.userDao().getUser()

}