package com.example.chatapp.domain.models.chat

import com.example.chatapp.domain.models.chat.Message

data class TextMessageModel(val text:String, override val time: String, override val senderId:String, override val receiverId :String, override val type:String = Message.Text)
    : Message {
        constructor() :this("","","","")
    }
