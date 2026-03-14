package com.example.wordmanagefilesystem.Mapper;

import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChatMapper {

    //查询聊天
    @Select("select * from chat_msg where match_id = #{matchId}")
    List<ChatBody> getChatMsg(@Param("matchId") String matchId);

    //发送消息
    @Insert("insert chat_msg(match_id, send_user_id, text, sender_img, send_time)\n" +
            "values(#{matchId}, #{sendUserId} ,#{text},#{senderImg},#{sendTime})")
    Integer sendChatMsg(@Param("matchId") String matchId , @Param("sendUserId") Integer sendUserId
            , @Param("text") String text, @Param("senderImg") String senderImg
            , @Param("sendTime")LocalDateTime sendTime);

    //获得发送方的头像
    @Select("select img from user_msg where user_id = #{userId}")
    String getSenderImg(@Param("userId") Integer userId);

}
