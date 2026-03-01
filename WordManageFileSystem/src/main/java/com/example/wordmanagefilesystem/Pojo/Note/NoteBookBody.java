package com.example.wordmanagefilesystem.Pojo.Note;

import lombok.Data;

import java.time.LocalDate;

/*高中总结一级单词 2025.2.24 1835个 98%*/
@Data
public class NoteBookBody {
    private Integer id;
    private Integer userId;
    private String bookName;
    private LocalDate createTime;
    private long wordCount;
    private double accurcayAvg;

    public NoteBookBody() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public long getWordCount() {
        return wordCount;
    }

    public void setWordCount(long wordCount) {
        this.wordCount = wordCount;
    }

    public double getAccurcayAvg() {
        return accurcayAvg;
    }

    public void setAccurcayAvg(double accurcayAvg) {
        this.accurcayAvg = accurcayAvg;
    }

    public NoteBookBody(Integer id, Integer userId, String bookName, LocalDate createTime, long wordCount, double accurcayAvg) {
        this.id = id;
        this.userId = userId;
        this.bookName = bookName;
        this.createTime = createTime;
        this.wordCount = wordCount;
        this.accurcayAvg = accurcayAvg;
    }
}
