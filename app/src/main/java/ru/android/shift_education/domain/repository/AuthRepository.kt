package ru.android.shift_education.domain.repository

import ru.android.shift_education.domain.entities.AuthEntity
import ru.android.shift_education.domain.entities.UserEntity
import ru.android.shift_education.presentation.appState.AppState

interface AuthRepository {

    suspend fun loginIntoApp(authEntity: AuthEntity): AppState<String>
    suspend fun registerInApp(authEntity: AuthEntity): AppState<UserEntity>
}