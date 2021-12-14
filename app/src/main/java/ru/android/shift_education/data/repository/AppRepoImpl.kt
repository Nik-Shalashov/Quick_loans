package ru.android.shift_education.data.repository

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import ru.android.shift_education.data.datasource.app.AppDataSource
import ru.android.shift_education.domain.repository.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(AppRepository::class, SingletonComponent::class)
class AppRepoImpl @Inject constructor(
    private val dataSource: AppDataSource
): AppRepository {
    override fun saveToken(token: String) {
        dataSource.saveToken(token)
    }

    override fun fetchToken(): String =
        dataSource.fetchToken()

    override fun isFirstTime(): Boolean =
        dataSource.isFirstTime()


    override fun setFirstTime() {
        dataSource.setFirstTime()
    }
}