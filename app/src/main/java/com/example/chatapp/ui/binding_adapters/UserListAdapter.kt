package com.example.chatapp.ui.binding_adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ListItemUserBinding
import com.example.chatapp.domain.User
import com.example.chatapp.ui.view_model.AppViewModel
import kotlin.math.log

class UserListAdapter(private val usersList: List<User>,
                      private val navController: NavController,private val viewModel:AppViewModel): RecyclerView.Adapter<UserViewHolder>() {

    var data = usersList
    lateinit var binding: ListItemUserBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.rootView.setOnClickListener {
            Log.i("Adapter", "onBindViewHolder: ${data[position].toString()}")

             viewModel.otherUserMessaging = data[position]

            Log.i("Adapter", "onBindViewHolder: ${viewModel.otherUserMessaging}")

            navController.navigate(R.id.action_peopleFragment_to_chatFragment)
        }
    }

}