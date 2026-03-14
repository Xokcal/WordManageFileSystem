package com.example.wordmanagefilesystem.Service;

import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;

import java.util.List;

public interface ChatService {
    public List<ChatBody> getChatMsg(Integer idA , Integer idB);
    public String sendChatMsg(ChatBody chatBody , Integer senderId , Integer idB);
}
