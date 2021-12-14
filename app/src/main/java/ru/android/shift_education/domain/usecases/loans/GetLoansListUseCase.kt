package ru.android.shift_education.domain.usecases.loans

import ru.android.shift_education.domain.repository.LoansRepository
import javax.inject.Inject

class GetLoansListUseCase @Inject constructor(
    private val repository: LoansRepository
) {
    suspend operator fun invoke() =
        repository.getLoansList()
}