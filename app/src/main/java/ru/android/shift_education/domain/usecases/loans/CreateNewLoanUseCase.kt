package ru.android.shift_education.domain.usecases.loans

import ru.android.shift_education.domain.entities.LoanRequestEntity
import ru.android.shift_education.domain.repository.LoansRepository
import javax.inject.Inject

class CreateNewLoanUseCase @Inject constructor(
    private val repository: LoansRepository
) {

    suspend operator fun invoke(loansRequestEntity: LoanRequestEntity) =
        repository.createNewLoan(loansRequestEntity)
}