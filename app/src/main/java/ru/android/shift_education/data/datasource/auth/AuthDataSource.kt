package ru.android.shift_education.data.datasource.auth

import retrofit2.Response
import ru.android.shift_education.domain.entities.AuthEntity
import ru.android.shift_education.domain.entities.UserEntity

interface AuthDataSource {

    suspend fun loginIntoApp(authEntity: AuthEntity): Response<String>
    suspend fun registerInApp(authEntity: AuthEntity): Response<UserEntity>
}