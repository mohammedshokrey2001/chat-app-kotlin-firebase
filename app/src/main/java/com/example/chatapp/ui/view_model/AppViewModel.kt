package com.example.chatapp.ui.view_model

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.domain.SignInModel
import com.example.chatapp.domain.SignUpModel
import com.example.chatapp.domain.User
import com.example.chatapp.ui.screens.CreateAccountFragment
import com.example.chatapp.ui.screens.SignInFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File
import kotlin.math.log


class AppViewModel(application: Application) : AndroidViewModel(application) {
    private var auth: FirebaseAuth = Firebase.auth

    var signUpDone = MutableLiveData(false)
    var signInDone = MutableLiveData(false)
    private val storage = Firebase.storage
    private val TAG = "ViewModel"
    lateinit var user: User
    private  var currentUserId: String =""

    // ...
// Initialize Firebase Auth
    fun signUp(signUpModel: SignUpModel, context: Context) {

        auth.createUserWithEmailAndPassword(signUpModel.mail, signUpModel.pass)
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser

                    currentUserId = user?.uid.toString()
                    val profileUpdates =
                        UserProfileChangeRequest.Builder().setDisplayName(signUpModel.name).build()

                    user!!.updateProfile(profileUpdates).addOnCompleteListener { name ->
                        if (name.isSuccessful) {

                            Log.i(TAG, "signUp: done name")
                        } else {
                            Log.i(TAG, "signUp: done not name")
                        }
                    }


                    signUpDone.postValue(true)

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)

                    CreateAccountFragment.mProgressDialog.cancel()
                    Toast.makeText(
                        context,
                        "please assure that pass is more than 6 char and didnt registered before",
                        Toast.LENGTH_LONG
                    ).show()

                }

            }
    }

    fun signIn(signInModel: SignInModel, context: Context) {

        Log.i(TAG, "signIn: $signInModel")
        auth.signInWithEmailAndPassword(signInModel.mail, signInModel.pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val userDemo = auth.currentUser
                    val img = storage.reference.root.child("profile_images")
                        .child(userDemo?.uid.toString() + ".jgp")
                    Log.i(TAG, "signInIMG: $img")
                    user = User(
                        mail = userDemo?.email.toString(),
                        profileImageUrl = "https://www.1min30.com/wp-content/uploads/2018/09/Symbole-Android.jpg",
                        status = "waiting", name = userDemo?.displayName.toString()
                    )

                    signInDone.postValue(true)
                    currentUserId = userDemo?.uid.toString()

                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    SignInFragment.mProgressDialog.cancel()
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()


                }

            }
    }


  fun  creteNewProfileImage( img : ByteArray){

      val riversRef = storage.reference.child("profile_images/$currentUserId+jpg")
     riversRef.putBytes(img).addOnCompleteListener{
         if (it.isSuccessful){
             Log.i("OwnFirebase", "creteNewProfileImage: done")
         }else{
             Log.i("OwnFirebase", "creteNewProfileImage: error")

         }

     }


    }
}