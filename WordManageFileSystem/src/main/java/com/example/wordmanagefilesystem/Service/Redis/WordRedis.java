package com.example.wordmanagefilesystem.Service.Redis;

import com.example.wordmanagefilesystem.Common.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Common.Redis.RedisUtil;
import com.example.wordmanagefilesystem.Pojo.Word;
import com.example.wordmanagefilesystem.Service.Implement.CheckValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Slf4j
@Component
public class WordRedis {
    @Autowired
    private RedisUtil redisUtil;

    public static final String REDIS_WORD_PAGE_SUCCESS = "缓存单词分页查询，缓存成功！！";

    //缓存单词分页查询
    public void putWordPage(Integer page , Integer pageSize , List<Word> words , Integer timeOut){
        String key = "word_page:page="+page+"&pageSize="+pageSize;
        Integer timeOutPlus = timeOut + new Random().nextInt(5);
        redisUtil.put(key , words , timeOutPlus);
        log.warn(REDIS_WORD_PAGE_SUCCESS);
    }

    public static final String GET_REDIS_WORD_PAGE_RESULT_ERROR = "获得缓存单词，结果错误！！";

    //获得缓存单词
    public Object getRedisWordPage(Integer page , Integer pageSize ){
        String key = "word_page:page="+page+"&pageSize="+pageSize;
        Object words = redisUtil.get(key);
        if (CheckValidUtil.isNull(words)){
            log.warn(GET_REDIS_WORD_PAGE_RESULT_ERROR);
            throw new BusinessExcept(GET_REDIS_WORD_PAGE_RESULT_ERROR ,500);
        }
        return words;
    }

    //查询有没有此缓存
    public boolean queryRedisWordPageIsExsist(Integer page , Integer pageSize ){
        String key = "word_page:page="+page+"&pageSize="+pageSize;
        Object words = redisUtil.get(key);
        return words == null ? false : true;
    }

    //删除所有缓存
    public void deleteRedisAllWordPage(){
        String key = "word_page:*";
        Set<String> keys = redisUtil.getKeys(key);
        redisUtil.deleteAllRedis(keys);
    }

}
