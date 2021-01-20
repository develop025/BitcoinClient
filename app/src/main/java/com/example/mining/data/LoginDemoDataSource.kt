package com.example.mining.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mining.network.ApiErrorResponse
import com.example.mining.network.ApiResponse
import com.example.mining.network.ApiSuccessResponse
import com.example.mining.network.MiningApiService
import com.example.mining.vo.LoggedInUser
import java.util.*

class LoginDemoDataSource : MiningApiService {

    override fun login(
        username: String,
        password: String
    ): LiveData<ApiResponse<LoggedInUser>> {
        return try {
            val fakeUser = LoggedInUser(
                UUID.randomUUID().toString(), "Jane Doe"
            )
            MutableLiveData(ApiSuccessResponse(fakeUser))
        } catch (e: Throwable) {
            MutableLiveData(ApiErrorResponse("Error logging in: ${e.message}"))
        }
    }

    override fun isUserAuthorized(username: String): LiveData<ApiResponse<Boolean>> {
        return MutableLiveData(ApiSuccessResponse(true))
    }

    override fun done(): LiveData<ApiResponse<Boolean>> {
        return MutableLiveData(ApiSuccessResponse(true))
    }
}