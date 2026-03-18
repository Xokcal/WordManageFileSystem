package com.example.wordmanagefilesystem.Common.Cache;

import com.example.wordmanagefilesystem.Common.Cache.Constant.ChatCacheConstant;
import com.example.wordmanagefilesystem.Common.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Common.Redis.RedisUtil;
import com.example.wordmanagefilesystem.Mapper.ChatMapper;
import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;
import com.example.wordmanagefilesystem.Service.Implement.CheckValidUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ChatCache {

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private RedisUtil redisUtil;

    //缓存所有聊天信息
//    @PostConstruct
    public void cacheChatAllMsg(){
        List<ChatBody> chatBodies = chatMapper.selectAllChatMsg();
        if (CheckValidUtil.isValid(chatBodies)){
            log.warn(ChatCacheConstant.CACHE_ALL_CHAT_SQL_RESULT_ERROR);
            throw new BusinessExcept(ChatCacheConstant.CACHE_ALL_CHAT_SQL_RESULT_ERROR ,500);
        }
        for (ChatBody chatBody : chatBodies) {
            redisUtil.putValueForever(ChatCacheConstant.REDIS_KEY_FIX_PREFIX + chatBody.getMatchId()
                    , chatBody.getText());
        }
        log.info(ChatCacheConstant.CACHE_ALL_CHAT_FINAL);
    }

    //获取所有聊天数据
    public List<Map<String , String>> getAllChatMsg(String matchId){
        if (CheckValidUtil.isValid(matchId)){
            log.warn(ChatCacheConstant.GET_ALL_CACHE_CHAT_MSG_PARAM_ERROR);
            throw new BusinessExcept(ChatCacheConstant.GET_ALL_CACHE_CHAT_MSG_PARAM_ERROR ,400);
        }
        return null;
    }
}
