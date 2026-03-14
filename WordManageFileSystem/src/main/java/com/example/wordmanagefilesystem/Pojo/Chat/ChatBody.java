package com.example.wordmanagefilesystem.Pojo.Chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatBody {
    private Integer id;
    private String matchId;
    private Integer sendUserId;
    private String text;
    private String senderImg;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendTime;

    public ChatBody() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderImg() {
        return senderImg;
    }

    public void setSenderImg(String senderImg) {
        this.senderImg = senderImg;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public ChatBody(Integer id, String matchId, Integer sendUserId, String text, String senderImg, LocalDateTime sendTime) {
        this.id = id;
        this.matchId = matchId;
        this.sendUserId = sendUserId;
        this.text = text;
        this.senderImg = senderImg;
        this.sendTime = sendTime;
    }
}
