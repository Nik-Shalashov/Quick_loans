package ru.android.shift_education.presentation.viewmodel.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.android.shift_education.domain.entities.AuthEntity
import ru.android.shift_education.domain.entities.UserEntity
import ru.android.shift_education.domain.usecases.app.FetchTokenUseCase
import ru.android.shift_education.domain.usecases.app.IsFirstTimeUseCase
import ru.android.shift_education.domain.usecases.app.SaveTokenUseCase
import ru.android.shift_education.domain.usecases.app.SetFirstTimeUseCase
import ru.android.shift_education.domain.usecases.auth.LoginIntoAppUseCase
import ru.android.shift_education.domain.usecases.auth.RegisterInAppUseCase
import ru.android.shift_education.presentation.appState.AppState
import ru.android.shift_education.presentation.fragments.InfoFragment
import ru.android.shift_education.presentation.fragments.MainFragment
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginIntoAppUseCase: LoginIntoAppUseCase,
    private val registerInAppUseCase: RegisterInAppUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val fetchTokenUseCase: FetchTokenUseCase,
    private val isFirstTimeUseCase: IsFirstTimeUseCase,
    private val setFirstTimeUseCase: SetFirstTimeUseCase
) : ViewModel() {

    private val _loginLiveData = MutableLiveData<AppState<String>>()
    val loginLiveData: LiveData<AppState<String>> = _loginLiveData
    private val _isValidateTextLiveData = MutableLiveData<Boolean>()
    val isValidateTextLiveData: LiveData<Boolean> = _isValidateTextLiveData
    private val _registerLiveData = MutableLiveData<AppState<UserEntity>>()
    val registerLiveData: LiveData<AppState<UserEntity>> = _registerLiveData
    private val _authLiveData = MutableLiveData<Boolean>()
    val authLiveData: LiveData<Boolean> = _authLiveData

    private val handlerLogin = CoroutineExceptionHandler { _, throwable ->
        _loginLiveData.postValue(AppState.Error(throwable.message))
    }

    private val handlerRegister = CoroutineExceptionHandler { _, throwable ->
        _registerLiveData.postValue(AppState.Error(throwable.message))
    }

    fun loginIntoApp(auth: AuthEntity) {
        viewModelScope.launch(
            handlerLogin
        ) {
            _loginLiveData.value = AppState.Loading
            val request: AppState<String> = loginIntoAppUseCase(auth)
            _loginLiveData.value = request
        }
    }

    fun registerInApp(auth: AuthEntity) {
        viewModelScope.launch(
            handlerRegister
        ) {
            _registerLiveData.value = AppState.Loading
            val request = registerInAppUseCase(auth)
            _registerLiveData.value = request
        }
    }

    fun saveAuthToken(token: String) {
        viewModelScope.launch(
            handlerLogin
        ) {
            saveTokenUseCase(token)
        }
    }

    fun chooseScreen(): Fragment {
        return if (isFirstTimeUseCase()) {
            setFirstTimeUseCase()
            InfoFragment.newInstance(Bundle())
        } else {
            MainFragment.newInstance(Bundle())
        }
    }

    fun isAuthorized() {
        viewModelScope.launch {
            _authLiveData.value = fetchTokenUseCase().isNotBlank()
        }
    }

    fun validateFields(auth: AuthEntity) {
        _isValidateTextLiveData.value = auth.name.isBlank() && auth.password.isBlank()
    }

}