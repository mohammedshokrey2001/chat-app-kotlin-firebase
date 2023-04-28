package com.example.chatapp.ui.view_model

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


class AppViewModel(application: Application) : AndroidViewModel(application) {
    private var auth: FirebaseAuth = Firebase.auth

    var signUpDone = MutableLiveData(false)
    var signInDone = MutableLiveData(false)
    private val storage = Firebase.storage
    private val TAG = "ViewModel"
    lateinit var user: User
    private var currentUserId: String = ""
    private lateinit var database: DatabaseReference


    init {
        database = Firebase.database.reference

    }

    // Initialize Firebase Auth
    fun signUp(signUpModel: SignUpModel, context: Context) {

        auth.createUserWithEmailAndPassword(signUpModel.mail, signUpModel.pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    creteNewProfileImage(assitance(signUpModel, context))

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
        var imageUrl =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Android_logo_2.svg/1024px-Android_logo_2.svg.png"
        Log.i(TAG, "signIn: $signInModel")
        auth.signInWithEmailAndPassword(signInModel.mail, signInModel.pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val userDemo = auth.currentUser
                    currentUserId = userDemo?.uid.toString()
                    val gsReference: StorageReference =
                        storage.getReferenceFromUrl("gs://chat-app-45ce8.appspot.com/profile_images/$currentUserId.jpg")
                    Log.i(TAG, "signIn: ${gsReference.toString()}")
                    gsReference.downloadUrl.addOnSuccessListener {
                        Log.i(TAG, "signIn: success ${it.toString()}")
                        imageUrl = it.toString()

                        user = User(
                            mail = userDemo?.email.toString(),
                            profileImageUrl = imageUrl,
                            status =   "waiting".toString() , name = userDemo?.displayName.toString()
                        )
                      getStatus()

                    }.addOnFailureListener {
                        Log.i(TAG, "signIn: success $it")
                    }

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


    fun creteNewProfileImage(img: ByteArray) {

        val riversRef = storage.reference.child("profile_images/$currentUserId.jpg")
        riversRef.putBytes(img).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("OwnFirebase", "creteNewProfileImage: done")

            } else {
                Log.i("OwnFirebase", "creteNewProfileImage: error")
            }
        }
    }

    fun assitance(signUpModel: SignUpModel, context: Context): ByteArray {
        val user = FirebaseAuth.getInstance().currentUser
        currentUserId = user?.uid.toString()
        val profileUpdates =
            UserProfileChangeRequest.Builder().setDisplayName(signUpModel.name).build()

        val res: Resources = context.resources
        val drawable: Drawable = res.getDrawable(com.example.chatapp.R.drawable.main)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitMapData = stream.toByteArray()
        user!!.updateProfile(profileUpdates).addOnCompleteListener { name ->
            if (name.isSuccessful) {

                Log.i(TAG, "signUp: done name")
            } else {
                Log.i(TAG, "signUp: done not name")
            }
        }
        return bitMapData

    }


    fun setData() {
        database.child("users").child(currentUserId).setValue(user)
    }
    fun changeStatus(status:String){
        database.child("users").child(currentUserId).child("status").setValue(status)
    }
    fun getStatus(){
        database.child("users").child(currentUserId).child("status").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            val demo = user
            user = User(name = demo.name, mail = demo.mail, profileImageUrl = demo.profileImageUrl, status = it.value.toString())
            setData()

            signInDone.postValue(true)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }



}