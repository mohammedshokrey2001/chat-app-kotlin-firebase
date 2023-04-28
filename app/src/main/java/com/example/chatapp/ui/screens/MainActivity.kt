package com.example.chatapp.ui.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chatapp.DestWithNoNavigationBarMenuView
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.ui.view_model.AppViewModel
import com.example.chatapp.ui.view_model.AppVmFactory


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

        if (!checkPermissionForReadExtertalStorage()){

            requestPermissionForReadExtertalStorage()
        }
    }

    private fun checkPermissionForReadExtertalStorage(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result: Int =
                this.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }


    @Throws(Exception::class)
    fun requestPermissionForReadExtertalStorage() {
        try {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION_REQUEST_CODE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }



    companion object{
       val  STORAGE_PERMISSION_CODE = 2005
        val TAG = "ACCESS"
         val READ_STORAGE_PERMISSION_REQUEST_CODE = 41

    }
}