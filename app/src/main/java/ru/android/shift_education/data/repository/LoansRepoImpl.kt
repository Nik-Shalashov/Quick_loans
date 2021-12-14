package ru.android.shift_education.data.repository

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import retrofit2.Response
import ru.android.shift_education.data.datasource.loans.LoansDataSource
import ru.android.shift_education.domain.entities.LoanConditionsEntity
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.domain.entities.LoanRequestEntity
import ru.android.shift_education.domain.repository.LoansRepository
import ru.android.shift_education.presentation.appState.AppState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(LoansRepository::class, SingletonComponent::class)
class LoansRepoImpl @Inject constructor(
    private val dataSource: LoansDataSource
): LoansRepository {

    override suspend fun createNewLoan(loansRequestEntity: LoanRequestEntity): AppState<LoanEntity> =
        checkResponse(dataSource.createNewLoan(loansRequestEntity))

    override suspend fun getLoanData(id: String): AppState<LoanEntity> =
        checkResponse(dataSource.getLoanData(id))

    override suspend fun getLoansList(): AppState<List<LoanEntity>> =
        checkResponse(dataSource.getLoansList())

    override suspend fun getLoanConditions(): AppState<LoanConditionsEntity> =
        checkResponse(dataSource.getLoanConditions())

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