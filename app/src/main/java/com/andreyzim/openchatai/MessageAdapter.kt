package com.andreyzim.openchatai

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreyzim.openchatai.databinding.ItemMessageBinding

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {

    private val list = messageList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        Log.i("RECYCLER", viewType.toString())
        return MessageViewHolder(
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class MessageViewHolder(
    private val binding: ItemMessageBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(message: Message) {
        if (message.messageType == MessageType.RECEIVED) {
            binding.receivedMessageLayout.visibility = View.VISIBLE
            if (message.showAvatar) binding.receivedMessageAvatar.visibility = View.VISIBLE
            if (message.showDate) binding.messageDate.visibility = View.VISIBLE
            binding.receivedMessageText.text = message.body
        } else {
            binding.sentMessageCard.visibility = View.VISIBLE
            if (message.showDate) binding.messageDate.visibility = View.VISIBLE
            binding.sentMessageText.text = message.body
        }
    }
}

data class Message(
    val body: String,
    val showAvatar: Boolean,
    val showDate: Boolean,
    val messageType: MessageType
)

enum class MessageType { RECEIVED, SENT }

val messageList = listOf(
    Message(
        body = "Hello!",
        showAvatar = true,
        showDate = true,
        messageType = MessageType.SENT
    ),
    Message(
        body = "Hello! I'm at your assistant.",
        showAvatar = true,
        showDate = false,
        messageType = MessageType.RECEIVED
    ),
    Message(
        body = "What's the weather today?",
        showAvatar = false,
        showDate = false,
        messageType = MessageType.SENT
    ),
    Message(
        body = "IDK",
        showAvatar = true,
        showDate = false,
        messageType = MessageType.RECEIVED
    ),
    Message(
        body = "bruh",
        showAvatar = false,
        showDate = false,
        messageType = MessageType.SENT
    ),
    Message(
        body = "Write a lond text message!",
        showAvatar = false,
        showDate = false,
        messageType = MessageType.SENT
    ),
    Message(
        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt enim sed vestibulum egestas. Nunc posuere ipsum et ultrices ullamcorper. Suspendisse nec cursus diam. Aliquam id dui sed lacus sodales ultrices. Vivamus facilisis ante non turpis cursus, ac tristique risus condimentum. Aliquam semper sollicitudin purus, fermentum consequat felis fringilla eget. Donec pulvinar, dolor vitae ullamcorper pharetra, arcu nisi faucibus nisl, quis dignissim dui sapien quis erat. Cras iaculis maximus eleifend. Nullam eu nibh mauris. Duis ut felis dui. Aenean orci mauris, venenatis et velit a, laoreet vehicula mi. Suspendisse diam leo, scelerisque malesuada finibus fringilla, sodales eu nibh. Vivamus lobortis orci et feugiat placerat. Mauris ullamcorper orci at vestibulum vehicula.",
        showAvatar = true,
        showDate = false,
        messageType = MessageType.RECEIVED
    ),
    Message(
        body = "Hello!",
        showAvatar = false,
        showDate = true,
        messageType = MessageType.SENT
    ),
    Message(
        body = "Hello! I'm at your assistant.",
        showAvatar = true,
        showDate = false,
        messageType = MessageType.RECEIVED
    ),
    Message(
        body = "What's the weather today?",
        showAvatar = false,
        showDate = false,
        messageType = MessageType.SENT
    ),
    Message(
        body = "IDK",
        showAvatar = true,
        showDate = false,
        messageType = MessageType.RECEIVED
    ),
    Message(
        body = "bruh",
        showAvatar = false,
        showDate = false,
        messageType = MessageType.SENT
    ),
    Message(
        body = "Write a lond text message!",
        showAvatar = false,
        showDate = false,
        messageType = MessageType.SENT
    ),
    Message(
        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt enim sed vestibulum egestas. Nunc posuere ipsum et ultrices ullamcorper. Suspendisse nec cursus diam. Aliquam id dui sed lacus sodales ultrices. Vivamus facilisis ante non turpis cursus, ac tristique risus condimentum. Aliquam semper sollicitudin purus, fermentum consequat felis fringilla eget. Donec pulvinar, dolor vitae ullamcorper pharetra, arcu nisi faucibus nisl, quis dignissim dui sapien quis erat. Cras iaculis maximus eleifend. Nullam eu nibh mauris. Duis ut felis dui. Aenean orci mauris, venenatis et velit a, laoreet vehicula mi. Suspendisse diam leo, scelerisque malesuada finibus fringilla, sodales eu nibh. Vivamus lobortis orci et feugiat placerat. Mauris ullamcorper orci at vestibulum vehicula.",
        showAvatar = true,
        showDate = false,
        messageType = MessageType.RECEIVED
    ),
)