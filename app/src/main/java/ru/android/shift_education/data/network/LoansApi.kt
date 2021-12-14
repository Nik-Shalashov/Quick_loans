package ru.android.shift_education.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.android.shift_education.domain.entities.*

interface LoansApi {

    @POST("login")
    suspend fun loginIntoApp(@Body authEntity: AuthEntity): Response<String>

    @POST("registration")
    suspend fun registerInApp(@Body authEntity: AuthEntity): Response<UserEntity>

    @POST("loans")
    suspend fun createNewLoan(@Body loansRequestEntity: LoanRequestEntity): Response<LoanEntity>

    @GET("loans/{id}")
    suspend fun getLoanData(
        @Path("id") loanId: String
    ): Response<LoanEntity>

    @GET("loans/all")
    suspend fun getLoansList(): Response<List<LoanEntity>>

    @GET("loans/conditions")
    suspend fun getLoanConditions(): Response<LoanConditionsEntity>
}