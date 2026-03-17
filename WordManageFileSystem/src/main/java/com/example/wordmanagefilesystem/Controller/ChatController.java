package com.example.wordmanagefilesystem.Controller;

import com.example.wordmanagefilesystem.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Pojo.Chat.AddFriendBody;
import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;
import com.example.wordmanagefilesystem.Pojo.Chat.SearchFriendBody;
import com.example.wordmanagefilesystem.Pojo.Result;
import com.example.wordmanagefilesystem.Service.ChatService;
import com.example.wordmanagefilesystem.Service.Constant.ChatConstant;
import com.example.wordmanagefilesystem.Service.Implement.ChatImpl;
import com.example.wordmanagefilesystem.Service.Implement.CheckValidUtil;
import com.example.wordmanagefilesystem.Tool.JWTTool;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatImpl chatImpl;

    @Autowired
    private JWTTool jwtTool;

    //查询聊天信息
    @GetMapping("/select")
    Result getChatMsg(@RequestHeader("userToken") String token , @RequestParam("idB") Integer idB){
        Claims claims = jwtTool.parseToken(token);
        List<ChatBody> chatMsg = chatImpl.getChatMsg((Integer) claims.get("id"), idB);
        return new Result().successChat(chatMsg);
    }

    //发送聊天信息
    @PostMapping("/send")
    Result sendChatMsg(@RequestBody ChatBody chatBody
            , @RequestHeader("userToken") String token , @RequestParam("idB") Integer idB){
        Claims claims = jwtTool.parseToken(token);
        String s = chatImpl.sendChatMsg(chatBody, (Integer) claims.get("id"), idB);
        return new Result().successChat(s);
    }

    //搜索好友
    @PostMapping("/search-friend")
    Result searchFriend(@Param("name") String name){
        List<SearchFriendBody> searchFriendBodies = chatImpl.searchFriendMatchName(name);
        return new Result().successChat(searchFriendBodies);
    }

    //添加好友
    @PostMapping("/add-friend")
    Result addFriend(@RequestHeader("userToken") String token , @RequestBody SearchFriendBody searchFriendBody){
        Claims claims = jwtTool.parseToken(token);
        String s = chatImpl.addFriendToBar((Integer) claims.get("id"), searchFriendBody);
        return new Result().successChat(s);
    }

    //查询用户添加的所有人信息
    @GetMapping("/select-all")
    Result selectUserAllFriend(@RequestHeader("userToken") String token){
        Claims claims = jwtTool.parseToken(token);
        List<AddFriendBody> allFriends = chatImpl.selectUserAllFriend((Integer) claims.get("id"));
        return new Result().successChat(allFriends);
    }

}





