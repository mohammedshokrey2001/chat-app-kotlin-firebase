package com.example.chatapp.ui.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chatapp.DestWithNoNavigationBarMenuView
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.ui.view_model.AppViewModel
import com.example.chatapp.ui.view_model.AppVmFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: AppViewModel by lazy {
        ViewModelProvider(this, AppVmFactory(this.application))[AppViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.navigationBar.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->


            if (DestWithNoNavigationBarMenuView.contains(destination.id)) {

                binding.navigationBar.visibility = View.GONE
            } else {

            binding.navigationBar.visibility = View.VISIBLE
            }

        }
    }
}