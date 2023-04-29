package com.example.chatapp.domain

data class ChatMessage(val text:String,  val time: String,  val senderId:String,  val receiverId :String,  val type:String )
{
    constructor() :this("","","","","")
}