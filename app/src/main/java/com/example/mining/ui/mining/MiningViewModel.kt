package com.example.mining.ui.mining

import androidx.lifecycle.*
import com.example.mining.data.Repository
import com.example.mining.util.AbsentLiveData
import com.example.mining.vo.Resource

class MiningViewModel(private val repository: Repository) : ViewModel() {
    private var _startMining = MutableLiveData<Boolean>(false)
    private val startMining: LiveData<Boolean>
        get() = _startMining

    private var _done: LiveData<Resource<Boolean>> = startMining.switchMap { start ->
        if (start) {
            repository.done()
        } else {
            AbsentLiveData.create()
        }
    }
    val done: LiveData<Resource<Boolean>>
        get() = _done

    fun onStartMining() {
        _startMining.value = true
    }
}
