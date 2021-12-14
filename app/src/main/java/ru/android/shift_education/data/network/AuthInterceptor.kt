package ru.android.shift_education.data.network

import okhttp3.*
import ru.android.shift_education.domain.usecases.app.FetchTokenUseCase
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val fetchTokenUseCase: FetchTokenUseCase
): Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = fetchTokenUseCase()
        val request = requestBuilder.addHeader("Authorization", token)
        return chain.proceed(request.build())
    }
}