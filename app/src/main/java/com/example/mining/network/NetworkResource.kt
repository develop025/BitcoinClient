package com.example.mining.network

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mining.util.AppExecutors
import com.example.mining.vo.Resource

/**
 * A generic class that can provide a resource backed by the network.
 *
 * @param <ResultType>
 *
 * </ResultType> */
abstract class NetworkResource<ResultType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        appExecutors.mainThread().execute {
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
                appExecutors.diskIO().execute {
                    when (response) {
                        is ApiSuccessResponse -> setValue(Resource.success(response.body))
                        is ApiEmptyResponse -> setValue(Resource.success(null))
                        is ApiErrorResponse -> setValue(Resource.error(response.errorMessage, null))
                    }
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<ResultType>>
}
