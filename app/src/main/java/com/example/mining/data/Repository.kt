package com.example.mining.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mining.network.ApiResponse
import com.example.mining.network.NetworkResource
import com.example.mining.preferencedata.SharedPreferenceHelper
import com.example.mining.util.AppExecutors
import com.example.mining.vo.LoggedInUser
import com.example.mining.vo.Resource

class Repository(val dataSource: LoginDemoDataSource, val appExecutors: AppExecutors) {

    companion object {
        const val USER_KEY = "USER"
    }

    fun login(username: String, password: String): LiveData<Resource<LoggedInUser>> {
        return object : NetworkResource<LoggedInUser>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<LoggedInUser>> {
                return dataSource.login(username, password)
            }
        }.asLiveData()
    }

    fun done(): LiveData<Resource<Boolean>> {
        return object : NetworkResource<Boolean>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<Boolean>> {
                return dataSource.done()
            }
        }.asLiveData()
    }

    fun isUserAuthorized(): LiveData<Resource<Boolean>> {
        SharedPreferenceHelper.getInstance().getString(USER_KEY)?.let { username ->
            return object : NetworkResource<Boolean>(appExecutors) {
                override fun createCall(): LiveData<ApiResponse<Boolean>> {
                    return dataSource.isUserAuthorized(username)
                }
            }.asLiveData()
        } ?: run {
            return MutableLiveData(Resource.error("", false))
        }
    }

    fun setLoggedInUser(loggedInUser: LoggedInUser) {
        SharedPreferenceHelper.getInstance().put(USER_KEY, loggedInUser.userId)
    }
}