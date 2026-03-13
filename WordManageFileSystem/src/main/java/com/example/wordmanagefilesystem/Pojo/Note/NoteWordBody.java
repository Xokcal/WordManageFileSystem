package com.example.wordmanagefilesystem.Pojo.Note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NoteWordBody {
    private Integer id;
    private Integer userId;
    private Integer noteId;
    private String word;
    private String meaning;
    private String partOfSpeech;
    private String belongGrade;
    private String similarWord;
    private String phrase;
    private Integer belong;

    public NoteWordBody() {
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

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getBelongGrade() {
        return belongGrade;
    }

    public void setBelongGrade(String belongGrade) {
        this.belongGrade = belongGrade;
    }

    public String getSimilarWord() {
        return similarWord;
    }

    public void setSimilarWord(String similarWord) {
        this.similarWord = similarWord;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Integer getBelong() {
        return belong;
    }

    public void setBelong(Integer belong) {
        this.belong = belong;
    }

    public NoteWordBody(Integer id, Integer userId, Integer noteId, String word, String meaning, String partOfSpeech, String belongGrade, String similarWord, String phrase, Integer belong) {
        this.id = id;
        this.userId = userId;
        this.noteId = noteId;
        this.word = word;
        this.meaning = meaning;
        this.partOfSpeech = partOfSpeech;
        this.belongGrade = belongGrade;
        this.similarWord = similarWord;
        this.phrase = phrase;
        this.belong = belong;
    }
}

/*
* id int primary key auto_increment comment '单词id',
    user_id int not null comment '用户id',
    note_id int not null comment '单词本id',
    word varchar(100) not null comment '单词',
    meaning varchar(100) not null comment '释义',
    part_of_speech varchar(20) comment '词性',
    belong_grade varchar(50) comment '所属阶段',
    similar_word varchar(200) comment '相似单词',
    phrase varchar(200) comment '短语',
    belong int comment '这里也不需要',*/
