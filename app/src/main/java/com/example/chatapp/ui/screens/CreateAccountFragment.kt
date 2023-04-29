package com.example.chatapp.ui.screens

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentCreateAccountBinding
import com.example.chatapp.domain.models.auth.SignUpModel
import com.example.chatapp.ui.view_model.AppViewModel


class CreateAccountFragment : Fragment() {

    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false)

       mProgressDialog = ProgressDialog(this.requireActivity())
       mProgressDialog.setTitle("Sign Up Dialog")
        mProgressDialog.setMessage("Please wait until perform Registering account")

        binding.createAccountButton2.setOnClickListener {
               mProgressDialog.show()
            createAcc();

        }
        viewModel.signUpDone.observe(this.requireActivity(), Observer {
            if (it==true){
                Toast.makeText(this.requireContext(),"you have been registered successfully ",Toast.LENGTH_LONG).show()
                binding.editTextEmail.text = null
                binding.editTextPassword.text = null
                binding.editTextDisplayName.text = null

                mProgressDialog.cancel()
                findNavController().navigate(R.id.action_createAccountFragment_to_signInFragment)
            }

        })

        return binding.root
    }

    private fun createAcc() {
        viewModel.signUp(
            SignUpModel(
                binding.editTextDisplayName.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            ),this.requireActivity()
        )

    }

    companion object{
        lateinit var  mProgressDialog :ProgressDialog

    }
}