package com.example.wordmanagefilesystem.Controller;

import com.example.wordmanagefilesystem.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;
import com.example.wordmanagefilesystem.Pojo.Result;
import com.example.wordmanagefilesystem.Service.ChatService;
import com.example.wordmanagefilesystem.Service.Implement.CheckValidUtil;
import com.example.wordmanagefilesystem.Tool.JWTTool;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private JWTTool jwtTool;

    //查询聊天信息
    @GetMapping("/select")
    Result getChatMsg(@RequestHeader("userToken") String token , @RequestParam("idB") Integer idB){
        Claims claims = jwtTool.parseToken(token);
        List<ChatBody> chatMsg = chatService.getChatMsg((Integer) claims.get("id"), idB);
        return new Result().successChat(chatMsg);
    }

    //发送聊天信息
    @PostMapping("/send")
    Result sendChatMsg(@RequestBody ChatBody chatBody
            , @RequestHeader("userToken") String token , @RequestParam("idB") Integer idB){
        Claims claims = jwtTool.parseToken(token);
        String s = chatService.sendChatMsg(chatBody, (Integer) claims.get("id"), idB);
        return new Result().successChat(s);
    }

}





