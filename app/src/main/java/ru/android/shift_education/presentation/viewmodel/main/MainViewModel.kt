package ru.android.shift_education.presentation.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.domain.usecases.loans.GetLoanDataUseCase
import ru.android.shift_education.domain.usecases.loans.GetLoansListUseCase
import ru.android.shift_education.presentation.appState.AppState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getLoanDataUseCase: GetLoanDataUseCase,
    private val getLoansListUseCase: GetLoansListUseCase,
): ViewModel() {

    private val _loansListLiveData = MutableLiveData<AppState<List<LoanEntity>>>()
    val loansListLiveData: LiveData<AppState<List<LoanEntity>>> = _loansListLiveData

    private val _loanDataLiveData = MutableLiveData<AppState<LoanEntity>>()
    val loanDataLiveData: LiveData<AppState<LoanEntity>> = _loanDataLiveData

    private val handlerList = CoroutineExceptionHandler { _, throwable ->
        _loansListLiveData.postValue(AppState.Error(throwable.message))
    }

    private val handlerData = CoroutineExceptionHandler { _, throwable ->
        _loanDataLiveData.postValue(AppState.Error(throwable.message))
    }

    fun getLoansList() {
        viewModelScope.launch(handlerList) {
            _loansListLiveData.value = AppState.Loading
            val loansList = getLoansListUseCase()
            _loansListLiveData.value = loansList
        }
    }

    fun getLoansData(id: String) {
        viewModelScope.launch(handlerData) {
            _loanDataLiveData.value = AppState.Loading
            val loanData = getLoanDataUseCase(id)
            _loanDataLiveData.value = loanData
        }
    }
}