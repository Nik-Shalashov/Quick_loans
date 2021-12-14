package ru.android.shift_education.data.datasource.loans

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import retrofit2.Response
import ru.android.shift_education.data.network.LoansApi
import ru.android.shift_education.domain.entities.LoanConditionsEntity
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.domain.entities.LoanRequestEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(LoansDataSource::class, SingletonComponent::class)
class LoansRemoteDataSource @Inject constructor(
    private val api: LoansApi
): LoansDataSource {

    override suspend fun createNewLoan(loansRequestEntity: LoanRequestEntity): Response<LoanEntity> =
        api.createNewLoan(loansRequestEntity)

    override suspend fun getLoanData(id: String): Response<LoanEntity> =
        api.getLoanData(id)

    override suspend fun getLoansList(): Response<List<LoanEntity>> =
        api.getLoansList()

    override suspend fun getLoanConditions(): Response<LoanConditionsEntity> =
        api.getLoanConditions()
}