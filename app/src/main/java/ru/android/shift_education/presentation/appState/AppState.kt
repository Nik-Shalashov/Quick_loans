package ru.android.shift_education.presentation.appState

sealed class AppState <out T> {
    data class Success<out R>(val content: R): AppState<R>()
    data class Error(val error: String?): AppState<Nothing>()
    object Loading: AppState<Nothing>()
}