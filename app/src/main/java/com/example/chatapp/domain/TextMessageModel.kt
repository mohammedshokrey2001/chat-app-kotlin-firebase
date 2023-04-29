package com.example.chatapp.domain

import java.util.Date

data class TextMessageModel(val text:String, override val time: String, override val senderId:String, override val receiverId :String, override val type:String =Message.Text)
    :Message{
        constructor() :this("","","","")
    }
