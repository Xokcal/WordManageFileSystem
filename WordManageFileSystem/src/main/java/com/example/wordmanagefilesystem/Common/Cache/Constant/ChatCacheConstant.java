package com.example.wordmanagefilesystem.Common.Cache.Constant;

import org.springframework.stereotype.Component;

@Component
public class ChatCacheConstant {
    public static final String CACHE_ALL_CHAT_SQL_RESULT_ERROR = "缓存所有聊天信息，获取所有信息失败";
    public static final String CACHE_ALL_CHAT_FINAL = "缓存所有聊天信息，数据储存完毕";
    public static final String REDIS_KEY_FIX_PREFIX = "chatMsg";
    public static final String GET_ALL_CACHE_CHAT_MSG_PARAM_ERROR = "获取所有聊天数据,参数错误";

}
