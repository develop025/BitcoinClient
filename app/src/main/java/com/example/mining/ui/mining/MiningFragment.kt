package com.example.mining.ui.mining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mining.R
import com.example.mining.ui.ViewModelFactory
import com.example.mining.vo.Status

class MiningFragment : Fragment() {

    private lateinit var miningViewModel: MiningViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mining, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val status = view.findViewById<TextView>(R.id.status)

        miningViewModel = ViewModelProvider(this, ViewModelFactory())
            .get(MiningViewModel::class.java)

        miningViewModel.onStartMining()

        miningViewModel.done.observe(this, Observer { response ->
            when (response.status) {
                Status.SUCCESS -> status.setText(R.string.ready_to_mining)
                Status.ERROR -> status.setText(R.string.server_request_error)
                Status.LOADING -> status.setText(R.string.request_in_process)
            }
        })
    }
}