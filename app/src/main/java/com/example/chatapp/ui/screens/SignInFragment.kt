package com.example.chatapp.ui.screens

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentSignInBinding
import com.example.chatapp.domain.SignInModel
import com.example.chatapp.domain.SignUpModel
import com.example.chatapp.ui.view_model.AppViewModel


class SignInFragment : Fragment() {

   private lateinit var binding :FragmentSignInBinding

   private val viewModel :AppViewModel by activityViewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in, container, false)

        binding.loginButton.setOnClickListener {
            performSignIn()

        }
        Log.i("TAG-Dest", "onCreateView: called")

        mProgressDialog = ProgressDialog(this.requireActivity())
        mProgressDialog.setTitle("Sign In Dialog")
        mProgressDialog.setMessage("Please wait until perform sign in")

        viewModel.signInDone.observe(viewLifecycleOwner, Observer {
            if (it==true){
                mProgressDialog.cancel()
                binding.editTextEmail.text = null
                binding.editTextPassword.text = null
                Log.i("TAG-Dest", "performSignIn: ${this.findNavController().currentDestination}")

                findNavController().navigate(R.id.action_signInFragment_to_peopleFragment)
            }

        })

        return binding.root
    }

    private fun performSignIn() {
        mProgressDialog.show()
        viewModel.signIn(
            SignInModel(binding.editTextEmail.text.toString(), pass = binding.editTextPassword.text.toString())
            ,this.requireContext()
        )

    }




    companion object {
        lateinit var  mProgressDialog :ProgressDialog

    }

}