package com.example.chatapp.ui.screens

import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentProfileBinding
import com.example.chatapp.ui.view_model.AppViewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream


// TODO: Rename parameter arguments, choose names that match

class ProfileFragment : Fragment() {

    private val viewModel: AppViewModel by activityViewModels()
    lateinit var binding: FragmentProfileBinding
   private val REQUEST_CODE = 200

    private lateinit var img :Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.viewmodel = viewModel

        binding.changeStatusButton.setOnClickListener {
            changeStatusDialog()
        }

        binding.changeImageButton.setOnClickListener {
            openGalleryForImages()
        }
        binding.logoutButton.setOnClickListener {
            viewModel.signInDone.postValue(false)
            findNavController().navigate(R.id.action_profileFragment_to_startFragment)
            Log.i("LogOut", "changeStatusDialog: navigate")
        }
        return binding.root
    }

    private fun changeStatusDialog() {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.change_status_dialog)
        dialog.show()

        val text = dialog.findViewById<EditText>(R.id.editTextStatus)
        val btOk = dialog.findViewById<Button>(R.id.button_ok)

        btOk.setOnClickListener {

            Log.i("Profile", "changeStatusDialog: ${text.text.toString()}")

            viewModel.changeStatus(text.text.toString())
            dialog.cancel()


        }




    }
    private fun openGalleryForImages() {

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE);


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
          if (data?.getData() != null) {
               img = data.data!!
              val d = convertImageToByte(img)
              viewModel.creteNewProfileImage(d!!)

            }
        }
    }


    private fun convertImageToByte(uri: Uri?): ByteArray? {
        var data: ByteArray? = null
        try {
            val cr: ContentResolver =this.requireContext().getContentResolver()
            val inputStream: InputStream? = cr.openInputStream(uri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            data = baos.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return data
    }

}