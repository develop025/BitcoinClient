package com.example.mining.network

import androidx.lifecycle.LiveData
import com.example.mining.vo.LoggedInUser
import retrofit2.http.GET

interface MiningApiService {
    @GET("login")
    fun login(username: String, password: String): LiveData<ApiResponse<LoggedInUser>>

    @GET("authorized")
    fun isUserAuthorized(username: String): LiveData<ApiResponse<Boolean>>

    @GET("done")
    fun done(): LiveData<ApiResponse<Boolean>>
}