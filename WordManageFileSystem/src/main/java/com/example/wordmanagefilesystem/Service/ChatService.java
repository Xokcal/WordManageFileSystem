package com.example.wordmanagefilesystem.Service;

import com.example.wordmanagefilesystem.Pojo.Chat.AddFriendBody;
import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;
import com.example.wordmanagefilesystem.Pojo.Chat.SearchFriendBody;

import java.util.List;

public interface ChatService {
    public List<ChatBody> getChatMsg(Integer idA , Integer idB);
    public String sendChatMsg(ChatBody chatBody , Integer senderId , Integer idB);
    public List<SearchFriendBody> searchFriendMatchName(String name);
    public String addFriendToBar(Integer userId , SearchFriendBody searchFriendBody);
    public List<AddFriendBody> selectUserAllFriend(Integer userId);
}
