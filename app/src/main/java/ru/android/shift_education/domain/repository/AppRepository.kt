package ru.android.shift_education.domain.repository

interface AppRepository {

    fun saveToken(token: String)
    fun fetchToken(): String
    fun isFirstTime(): Boolean
    fun setFirstTime()
}