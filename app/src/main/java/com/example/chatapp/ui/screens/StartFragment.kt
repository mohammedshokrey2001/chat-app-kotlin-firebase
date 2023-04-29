package com.example.chatapp.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentStartBinding
import com.example.chatapp.domain.shared_pref.AppSharedPref
import com.example.chatapp.ui.view_model.AppViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {


    lateinit var binding:FragmentStartBinding


    val viewModel :AppViewModel by activityViewModels ( )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_start, container, false)
        val sharePref = AppSharedPref(this.requireActivity())
        if (sharePref.getLoggedInState()){

            viewModel.user = sharePref.getUserData()

            findNavController().navigate(R.id.action_startFragment_to_peopleFragment)
        }
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_signInFragment)
        }
        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_createAccountFragment)
        }


        return binding.root
    }



}