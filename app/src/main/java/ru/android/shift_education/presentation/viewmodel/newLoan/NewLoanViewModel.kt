package ru.android.shift_education.presentation.viewmodel.newLoan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.android.shift_education.domain.entities.LoanConditionsEntity
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.domain.entities.LoanRequestEntity
import ru.android.shift_education.domain.usecases.loans.CreateNewLoanUseCase
import ru.android.shift_education.domain.usecases.loans.GetLoanConditionsUseCase
import ru.android.shift_education.presentation.appState.AppState
import javax.inject.Inject

@HiltViewModel
class NewLoanViewModel @Inject constructor(
    private val createNewLoanUseCase: CreateNewLoanUseCase,
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase
): ViewModel() {

    private val _loanConditionsLiveData = MutableLiveData<AppState<LoanConditionsEntity>>()
    val loanConditionsLiveData: LiveData<AppState<LoanConditionsEntity>> = _loanConditionsLiveData

    private val _newLoanLiveData = MutableLiveData<AppState<LoanEntity>>()
    val newLoanLiveData: LiveData<AppState<LoanEntity>> = _newLoanLiveData

    private val handlerConditions = CoroutineExceptionHandler { _, throwable ->
        _loanConditionsLiveData.postValue(AppState.Error(throwable.message))
    }

    private val handlerNewLoan = CoroutineExceptionHandler { _, throwable ->
        _newLoanLiveData.postValue(AppState.Error(throwable.message))
    }

    fun getLoanConditions() {
        viewModelScope.launch(handlerConditions) {
            _loanConditionsLiveData.value = AppState.Loading
            val conditions = getLoanConditionsUseCase()
            _loanConditionsLiveData.value = conditions

        }
    }

    fun createNewLoan(loanRequestEntity: LoanRequestEntity) {
        viewModelScope.launch(handlerNewLoan) {
            _newLoanLiveData.value = AppState.Loading
            val newLoan = createNewLoanUseCase(loanRequestEntity)
            _newLoanLiveData.value = newLoan
        }

    }
}