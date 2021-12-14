package ru.android.shift_education.data.datasource.auth

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import retrofit2.Response
import ru.android.shift_education.data.network.LoansApi
import ru.android.shift_education.domain.entities.AuthEntity
import ru.android.shift_education.domain.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(AuthDataSource::class, SingletonComponent::class)
class AuthRemoteDataSource @Inject constructor(
    private val api: LoansApi
): AuthDataSource {

    override suspend fun loginIntoApp(authEntity: AuthEntity) =
        api.loginIntoApp(authEntity)

    override suspend fun registerInApp(authEntity: AuthEntity) =
        api.registerInApp(authEntity)

}