package com.example.mining.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mining.data.LoginDemoDataSource
import com.example.mining.data.Repository
import com.example.mining.ui.authorizationcheck.AuthorizationCheckViewModel
import com.example.mining.ui.login.LoginViewModel
import com.example.mining.ui.mining.MiningViewModel
import com.example.mining.util.AppExecutors
import java.util.concurrent.Executors

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dataSource =
            LoginDemoDataSource()//todo For release change to MiningApi.miningApiService
        val appExecutors = AppExecutors(
            Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(5)
        )

        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(
                    repository = Repository(
                        dataSource = dataSource,
                        appExecutors = appExecutors
                    )
                ) as T
            }
            modelClass.isAssignableFrom(AuthorizationCheckViewModel::class.java) -> {
                return AuthorizationCheckViewModel(
                    repository = Repository(
                        dataSource = dataSource,
                        appExecutors = appExecutors
                    )
                ) as T
            }
            modelClass.isAssignableFrom(MiningViewModel::class.java) -> {
                return MiningViewModel(
                    repository = Repository(
                        dataSource = dataSource,
                        appExecutors = appExecutors
                    )
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}