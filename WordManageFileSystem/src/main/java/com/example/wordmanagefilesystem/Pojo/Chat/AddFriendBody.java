package com.example.wordmanagefilesystem.Pojo.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendBody {
    private Integer id;
    private Integer userId;
    private Integer addId;
    private String msgName;
    private String msgImg;


}
