package com.example.wordmanagefilesystem.Service.Implement;

import com.example.wordmanagefilesystem.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Mapper.ChatMapper;
import com.example.wordmanagefilesystem.Pojo.Chat.AddFriendBody;
import com.example.wordmanagefilesystem.Pojo.Chat.ChatBody;
import com.example.wordmanagefilesystem.Pojo.Chat.SearchFriendBody;
import com.example.wordmanagefilesystem.Pojo.Msg.MsgBody;
import com.example.wordmanagefilesystem.Service.ChatService;
import com.example.wordmanagefilesystem.Service.Constant.ChatConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new BusinessExcept(ChatConstant.GET_CHAT_RESULT_ERROR , 500);
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

    //搜索好友
    @Override
    public List<SearchFriendBody> searchFriendMatchName(String name){
        if (CheckValidUtil.isValid(name)){
            log.warn(ChatConstant.SEARCH_PARAM_ERROR);
            throw new BusinessExcept(ChatConstant.SEARCH_PARAM_ERROR ,400);
        }
        List<SearchFriendBody> searchFriendBodies = chatMapper.searchFriend(name);
        if (CheckValidUtil.isValid(searchFriendBodies)){
            log.warn(ChatConstant.SEARCH_RESULT_ERROR);
            throw new BusinessExcept(ChatConstant.SEARCH_RESULT_ERROR ,500);
        }
        return searchFriendBodies;
    }

    //添加好友
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addFriendToBar(Integer userId , SearchFriendBody searchFriendBody){
        if (CheckValidUtil.isNull(searchFriendBody)){
            log.warn(ChatConstant.ADD_FRIEND_PARAM_ERROR);
            throw new BusinessExcept(ChatConstant.ADD_FRIEND_PARAM_ERROR ,400);
        }
        if (isRepeationAddFriend(userId , searchFriendBody.getId())){
            log.warn(ChatConstant.ADD_FRIEND_REPEATION);
            throw new BusinessExcept(ChatConstant.ADD_FRIEND_REPEATION ,500);
        }
        if (!mirrorStoreAddFriend(userId , searchFriendBody.getId())){
            log.warn(ChatConstant.ADD_FRIEND_MIRROR_STORE_DEFEAT);
            throw new BusinessExcept(ChatConstant.ADD_FRIEND_MIRROR_STORE_DEFEAT ,500);
        }
        Integer row = chatMapper.addFriend(userId , searchFriendBody.getId()
                , searchFriendBody.getName() , searchFriendBody.getImg());

        return row > 0 ? ChatConstant.ADD_FRIEND_RESULT_RIGHT :
                ChatConstant.ADD_FRIEND_RESULT_ERROR;
    }

    //有没有重复加好友
    private boolean isRepeationAddFriend(Integer userId , Integer addId){
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(addId)){
            log.warn(ChatConstant.IS_REPEATION_PARAM_ERROR);
            throw new BusinessExcept(ChatConstant.IS_REPEATION_PARAM_ERROR ,400);
        }
        Integer isRepeation = chatMapper.isRepeationAddFriend(userId, addId);
        return isRepeation > 0;
    }

    //查询用户添加的所有人信息
    @Override
    public List<AddFriendBody> selectUserAllFriend(Integer userId) {
        if (CheckValidUtil.isNull(userId)) {
            log.warn(ChatConstant.SELECT_ALL_FRIEND_PARAM_ERROR);
            throw new BusinessExcept(ChatConstant.SELECT_ALL_FRIEND_PARAM_ERROR, 400);
        }
        List<AddFriendBody> allFriends = chatMapper.selectUserAllFriend(userId);
        if (CheckValidUtil.isValid(allFriends)) {
            log.warn(ChatConstant.SELECT_ALL_FRIEND_RESULT_ERROR);
            throw new BusinessExcept(ChatConstant.SELECT_ALL_FRIEND_RESULT_ERROR, 500);
        }
        return allFriends;
    }



    //镜像储存add_friend，将添加和被添加的id互换，确保双方都有对方好友
    private boolean mirrorStoreAddFriend(Integer userId , Integer addId){
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(addId)){
            log.warn(ChatConstant.MIRROR_STORE_PARAM_ERROR);
            throw new BusinessExcept(ChatConstant.MIRROR_STORE_PARAM_ERROR ,400);
        }
        MsgBody msg = chatMapper.getUserMsg(userId);
        if(CheckValidUtil.isNull(msg)){
            log.warn(ChatConstant.MIRROR_SELECT_USER_MSG_RESULT_ERROR);
            throw new BusinessExcept(ChatConstant.MIRROR_SELECT_USER_MSG_RESULT_ERROR ,400);
        }
        Integer r = chatMapper.addFriend(addId, userId, msg.getName(), msg.getImg());
        return r > 0;
    }



}

