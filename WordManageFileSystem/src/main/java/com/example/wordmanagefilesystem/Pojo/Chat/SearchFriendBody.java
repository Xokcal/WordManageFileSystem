package com.example.wordmanagefilesystem.Pojo.Chat;

import lombok.Data;

@Data
public class SearchFriendBody {
    private Integer id;
    private String name;
    private String img;
    private String gender;

    public SearchFriendBody() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public SearchFriendBody(Integer id, String name, String img, String gender) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.gender = gender;
    }
}
