package com.example.mining.ui.login

import android.util.Patterns
import androidx.lifecycle.*
import com.example.mining.data.Repository
import com.example.mining.vo.LoggedInUser
import com.example.mining.R
import com.example.mining.util.AbsentLiveData
import com.example.mining.vo.LoginData
import com.example.mining.vo.Resource

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _loginData = MutableLiveData<LoginData?>()
    private val loginData: LiveData<LoginData?>
        get() = _loginData

    private var _loginResult: LiveData<Resource<LoggedInUser>> =
        loginData.switchMap { login ->
            if (login == null) {
                AbsentLiveData.create()
            } else {
                repository.login(login.username, login.password)
            }
        }

    val loginResult: LiveData<Resource<LoggedInUser>>
        get() = _loginResult

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun login(username: String, password: String) {
        _loginData.value = LoginData(username, password)
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun onLogin(loggedInUser: LoggedInUser) {
        repository.setLoggedInUser(loggedInUser)
    }
}