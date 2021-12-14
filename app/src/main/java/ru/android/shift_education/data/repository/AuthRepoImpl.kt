package ru.android.shift_education.data.repository

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import retrofit2.Response
import ru.android.shift_education.data.datasource.auth.AuthDataSource
import ru.android.shift_education.domain.entities.AuthEntity
import ru.android.shift_education.domain.entities.UserEntity
import ru.android.shift_education.domain.repository.AuthRepository
import ru.android.shift_education.presentation.appState.AppState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(AuthRepository::class, SingletonComponent::class)
class AuthRepoImpl @Inject constructor(
    private val dataSource: AuthDataSource
): AuthRepository {

    override suspend fun loginIntoApp(authEntity: AuthEntity): AppState<String> =
        checkResponse(dataSource.loginIntoApp(authEntity))


    override suspend fun registerInApp(authEntity: AuthEntity): AppState<UserEntity> =
        checkResponse(dataSource.registerInApp(authEntity))

    private fun <T> checkResponse(response: Response<T>): AppState<T> {
        return if (response.isSuccessful) AppState.Success(response.body()!!)
        else {
            val message = when (response.code()) {
                401 -> "Пользователь не авториазован!"
                403 -> "Отказано в доступе!"
                404 -> "Error 404 Not found"
                else -> "Unknown error!"
            }
            AppState.Error(message)
        }
    }
}

