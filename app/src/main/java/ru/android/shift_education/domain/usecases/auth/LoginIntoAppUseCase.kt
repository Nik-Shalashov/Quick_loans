package ru.android.shift_education.domain.usecases.auth

import ru.android.shift_education.domain.entities.AuthEntity
import ru.android.shift_education.domain.repository.AuthRepository
import javax.inject.Inject

class LoginIntoAppUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(authEntity: AuthEntity) =
        repository.loginIntoApp(authEntity)
}