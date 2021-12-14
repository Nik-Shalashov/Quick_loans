package ru.android.shift_education.data.datasource.loans

import retrofit2.Response
import ru.android.shift_education.domain.entities.LoanConditionsEntity
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.domain.entities.LoanRequestEntity

interface LoansDataSource {

    suspend fun createNewLoan(loansRequestEntity: LoanRequestEntity): Response<LoanEntity>
    suspend fun getLoanData(id: String): Response<LoanEntity>
    suspend fun getLoansList(): Response<List<LoanEntity>>
    suspend fun getLoanConditions(): Response<LoanConditionsEntity>
}