package ru.android.shift_education.domain.repository

import ru.android.shift_education.domain.entities.LoanConditionsEntity
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.domain.entities.LoanRequestEntity
import ru.android.shift_education.presentation.appState.AppState

interface LoansRepository {

    suspend fun createNewLoan(loansRequestEntity: LoanRequestEntity): AppState<LoanEntity>
    suspend fun getLoanData(id: String): AppState<LoanEntity>
    suspend fun getLoansList(): AppState<List<LoanEntity>>
    suspend fun getLoanConditions(): AppState<LoanConditionsEntity>
}