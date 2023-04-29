package com.example.chatapp.domain.models.chat

data class ChatMessage(val text:String,  val time: String,  val senderId:String,  val receiverId :String,  val type:String )
{
    constructor() :this("","","","","")
}