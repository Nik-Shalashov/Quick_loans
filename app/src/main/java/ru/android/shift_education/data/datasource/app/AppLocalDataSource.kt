package ru.android.shift_education.data.datasource.app

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(AppDataSource::class, SingletonComponent::class)
class AppLocalDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context
): AppDataSource {

    companion object {
        const val SHARED_PREFERENCES = "AppSettings"
        const val TOKEN = "token"
        const val FIRST_TIME = "firstTime"
    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        sharedPreferences.edit {
            putString(TOKEN, token)
        }
    }

    override fun fetchToken(): String =
        sharedPreferences.getString(TOKEN, "") ?: ""

    override fun isFirstTime(): Boolean =
        sharedPreferences.getBoolean(FIRST_TIME, true)

    override fun setFirstTime() {
        sharedPreferences.edit {
            putBoolean(FIRST_TIME, false)
        }
    }
}

