package com.example.mining.ui.authorizationcheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.mining.data.Repository
import com.example.mining.util.AbsentLiveData
import com.example.mining.vo.Resource

class AuthorizationCheckViewModel(private val repository: Repository) : ViewModel() {
    private val _startCheck = MutableLiveData<Boolean>(false)
    private val startCheck: LiveData<Boolean>
        get() = _startCheck

    private var _checkResult: LiveData<Resource<Boolean>> = startCheck.switchMap { check ->
        if (check) {
            repository.isUserAuthorized()
        } else {
            AbsentLiveData.create()
        }
    }

    val checkResult: LiveData<Resource<Boolean>>
        get() = _checkResult

    init {
        isUserAuthorization()
    }

    private fun isUserAuthorization() {
        _startCheck.value = true
    }
}