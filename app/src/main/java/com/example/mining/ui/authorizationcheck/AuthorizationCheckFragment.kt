package com.example.mining.ui.authorizationcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mining.R
import com.example.mining.ui.ViewModelFactory
import com.example.mining.vo.Status

class AuthorizationCheckFragment : Fragment() {
    private lateinit var checkViewModel: AuthorizationCheckViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_authorization_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkViewModel = ViewModelProvider(this, ViewModelFactory())
            .get(AuthorizationCheckViewModel::class.java)

        checkViewModel.checkResult.observe(this, Observer { checkResult ->

            if (checkResult.status == Status.SUCCESS && checkResult.data as Boolean) {
                findNavController().navigate(R.id.action_mining)
            } else if (checkResult.status == Status.ERROR) {
                findNavController().navigate(R.id.action_authorizationCheckFragment_to_loginFragment)
            }
        })
    }
}