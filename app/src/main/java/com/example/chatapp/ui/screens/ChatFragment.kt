package com.example.chatapp.ui.screens

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.domain.models.chat.ChatMessage
import com.example.chatapp.domain.models.chat.TextMessageModel
import com.example.chatapp.ui.binding_adapters.MessagesListAdapter
import com.example.chatapp.ui.view_model.AppViewModel
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    val viewModel: AppViewModel by activityViewModels()

    private lateinit var rv :RecyclerView
     var chatList = ArrayList<ChatMessage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        binding.viewmodel = viewModel


           rv = binding.messagesRecyclerView
        binding.sendBtn.setOnClickListener {
            if (binding.editTextMessage.text.toString().isEmpty()) {
                Toast.makeText(
                    this.requireContext(),
                    "message is blank please fill it",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val sdf = SimpleDateFormat("yyyy.MM.dd  HH:mm")
                val currentDateandTime: String = sdf.format(Date())
                viewModel.sendMessage(
                    messageTextMessageModel = TextMessageModel(
                        binding.editTextMessage.text.toString(),
                        currentDateandTime, viewModel.user.id, viewModel.otherUserMessaging.id
                    )
                )

                binding.editTextMessage.text = null
            }
        }

        readMessage(viewModel.user.id,viewModel.otherUserMessaging.id)
        return binding.root

    }


    fun readMessage(senderId: String, receiverId: String) {

        val databaseReference = FirebaseDatabase.getInstance().getReference("chat")

        databaseReference.addValueEventListener( object : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {

                    val chat = dataSnapShot.getValue(ChatMessage::class.java)

                    if (chat!!.senderId == senderId && chat!!.receiverId == receiverId
                        || chat!!.senderId == receiverId && chat!!.receiverId == senderId
                    ) {
                        chatList.add(chat)
                    }
                }

                val chatAdapter = MessagesListAdapter(senderId, chatList)

                rv.adapter = chatAdapter

            }
        })

    }

}