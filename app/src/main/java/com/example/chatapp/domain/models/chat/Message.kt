package com.example.chatapp.domain.models.chat

interface Message {
    val time : String
    val senderId:String
    val type:String
    val receiverId :String

    companion object{
        const val Text = "TEXT"
        const val IMAGE = "IMAGE"

    }
}