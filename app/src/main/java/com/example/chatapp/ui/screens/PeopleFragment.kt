package com.example.chatapp.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentPeopleBinding
import com.example.chatapp.ui.binding_adapters.UserListAdapter
import com.example.chatapp.ui.view_model.AppViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PeopleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PeopleFragment : Fragment() {

    private  lateinit var binding :FragmentPeopleBinding
    private val viewModel :AppViewModel by activityViewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=  DataBindingUtil.inflate(inflater,R.layout.fragment_people, container, false)
        viewModel.getAllUsers()

        val rv = binding.chatsRecyclerView
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this.requireContext())
        val userAdapter = UserListAdapter(viewModel.listOfUsers,findNavController(),viewModel)
        rv.adapter = userAdapter

        viewModel.newUsersDone.observe(this.requireActivity(), Observer {

            userAdapter.data = viewModel.listOfUsers
            userAdapter.notifyDataSetChanged()
        //    viewModel.createChannels()


        })



        return binding.root
    }


}