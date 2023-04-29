package com.example.chatapp.ui.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.chatapp.databinding.ListItemMessageSentBinding
import com.example.chatapp.databinding.ListItemUserBinding
import com.example.chatapp.domain.models.chat.ChatMessage
import com.example.chatapp.domain.models.user.User


@BindingAdapter("bind_image_url_blur", requireAll = true)
fun ImageView.setImageFromUrl(url:String){
    this.load(url)
}



class UserViewHolder(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.user = user
    }
}

class ChatViewHolderSent(
    private val binding: ListItemMessageSentBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(message: ChatMessage) {
        binding.message = message
    }

}

class ChatViewHolderReceiver(
    private val binding: ListItemMessageSentBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(message: ChatMessage) {
        binding.message = message
    }

}






object Adapters {
    const val SEND = "SEND"
    const val RECEIVE = "RECEIVE"

}

