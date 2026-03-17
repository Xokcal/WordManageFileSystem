package com.example.wordmanagefilesystem.Mapper;

import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;
import com.example.wordmanagefilesystem.Pojo.Chat.AddFriendBody;
import com.example.wordmanagefilesystem.Pojo.Chat.SearchFriendBody;
import com.example.wordmanagefilesystem.Pojo.Msg.MsgBody;
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

    //搜索好友
    @Select("select u.id , um.name , um.img ,um.gender from user u left join " +
            "user_msg um on u.id = um.user_id where um.name like concat('%',#{name},'%')")
    List<SearchFriendBody> searchFriend(@Param("name") String name);

    //将搜索到的好友添加到好友栏(添加好友)
    @Insert("insert into add_friend(user_id, add_id, msg_name, msg_img) " +
            "values (#{userId}, #{addId}, #{msgName}, #{msgImg})")
    Integer addFriend(@Param("userId") Integer userId, @Param("addId") Integer addId
            ,@Param("msgName") String msgName, @Param("msgImg") String msgImg);

    //判断是否重复加同一个用户
    @Select("select count(*) from add_friend where user_id = #{userId} and add_id = #{addId}")
    Integer isRepeationAddFriend(@Param("userId") Integer userId , @Param("addId") Integer addId);

    //查询用户添加的所有人信息
    @Select("select * from add_friend where user_id = #{userId}")
    List<AddFriendBody> selectUserAllFriend(@Param("userId") Integer userId);

    //获得用户的user_msg信息
    @Select("select * from user_msg where user_id = #{userId}")
    MsgBody getUserMsg(@Param("userId") Integer userId);
}
