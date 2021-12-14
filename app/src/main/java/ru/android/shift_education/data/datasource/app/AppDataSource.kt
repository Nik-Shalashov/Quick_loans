package ru.android.shift_education.data.datasource.app

interface AppDataSource {

    fun saveToken(token: String)
    fun fetchToken(): String
    fun isFirstTime(): Boolean
    fun setFirstTime()
}