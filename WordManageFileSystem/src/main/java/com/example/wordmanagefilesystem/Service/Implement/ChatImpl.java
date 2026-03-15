package com.example.wordmanagefilesystem.Service.Implement;

import com.example.wordmanagefilesystem.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Mapper.ChatMapper;
import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;
import com.example.wordmanagefilesystem.Service.ChatService;
import com.example.wordmanagefilesystem.Service.Constant.ChatConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.wordmanagefilesystem.Service.Constant.ChatConstant.GET_CHAT_ID_ERROR;

@Slf4j
@Service
public class ChatImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;

    //查询聊天信息
    @Override
    public List<ChatBody> getChatMsg(Integer idA , Integer idB){
        if (idA <= 0 || idB <= 0 || CheckValidUtil.isNull(idA) || CheckValidUtil.isNull(idB)) {
            log.warn(ChatConstant.GET_CHAT_ID_ERROR);
            throw new BusinessExcept(GET_CHAT_ID_ERROR , 400);
        }
        List<ChatBody> chatMsg = chatMapper.getChatMsg(getMatchId(idA, idB));
        if (CheckValidUtil.isValid(chatMsg)){
            log.warn(ChatConstant.GET_CHAT_RESULT_ERROR);
            throw new BusinessExcept(ChatConstant.GET_CHAT_RESULT_ERROR , 400);
        }
        return chatMsg;
    }

    //将双方id转化为matchId
    private static String getMatchId(Integer idA , Integer idB){
        return Math.min(idA , idB) + "_" + Math.max(idA , idB);
    }

    //发送聊天信息
    @Override
    public String sendChatMsg(ChatBody chatBody , Integer senderId , Integer idB){
        if (CheckValidUtil.isNull(chatBody) || CheckValidUtil.isNull(senderId) || CheckValidUtil.isNull(idB)){
            log.warn(ChatConstant.SEND_CHAT_PARAM_ERROR);
            throw new BusinessExcept(ChatConstant.SEND_CHAT_PARAM_ERROR , 400);
        }
        String senderImg = getSenderImg(senderId);
        Integer integer = chatMapper.sendChatMsg(getMatchId(senderId, idB), senderId
                , chatBody.getText(), senderImg, LocalDateTime.now());
        return integer > 0 ? ChatConstant.SEND_CHAT_INSERT_RIGHT :
                ChatConstant.SEND_CHAT_INSERT_ERROR;
    }

    //得到发送方的头像
    private String getSenderImg(Integer senderId){
        String senderImg = chatMapper.getSenderImg(senderId);
        if (CheckValidUtil.isValid(senderImg)){
            log.warn(ChatConstant.SENDER_IMG_ERROR);
            throw new BusinessExcept(ChatConstant.SENDER_IMG_ERROR ,400);
        }
        return senderImg;
    }

}

