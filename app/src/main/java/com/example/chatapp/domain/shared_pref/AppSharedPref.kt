package com.example.chatapp.domain.shared_pref

import android.content.Context
import android.content.SharedPreferences
import com.example.chatapp.domain.models.user.User


class AppSharedPref(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(MY_PREF, 0)
        editor = sharedPreferences.edit()
    }

    private fun setInt(key: String?, value: Int?) {
        editor.putInt(key, value!!)
        editor.commit()
    }

    private fun setString(key: String?, value: String?){
        editor.putString(key, value!!)
        editor.commit()
    }

    private fun setBoolean(key: String?, value: Boolean?){
        editor.putBoolean(key, value!!)
        editor.commit()
    }

    private fun getBoolean(key: String?):Boolean{
       return sharedPreferences.getBoolean(key,false)
    }

    private fun getInt(key: String?): Int? {
        return sharedPreferences.getInt(key, 0)
    }

    private fun getString(key: String):String{
        return sharedPreferences.getString(key,"null")!!
    }


   private fun clear(key: String?) {
        editor.remove(key)
        editor.commit()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }
    fun getLoggedInState() :Boolean{
        return getBoolean(LOGGED_IN_STATE)
    }

    fun setLoggedInState(state:Boolean){
        setBoolean(LOGGED_IN_STATE,state)
    }


    fun setUserData(user:User){
        setString(NAME,user.name);
        setString(MAIL,user.mail);
        setString(STATUS,user.status);
        setString(PROFILE_IMAGE,user.profileImageUrl);
        setString(ID,user.id);

    }

    fun getUserData() :User{

        return User(
            name = getString(NAME),
            id = getString(ID),
            profileImageUrl = getString(PROFILE_IMAGE),
            status = getString(STATUS)
        , mail = getString(MAIL)
        )

    }


    companion object {
        const val MY_PREF = "MyPreferences"
        const val LOGGED_IN_STATE = "log_in_state"
        const val NAME = "name"
        const val STATUS = "status"
        const val ID = "id"
        const val PROFILE_IMAGE = "profile_img_url"
        const val MAIL = "mail"


    }




}