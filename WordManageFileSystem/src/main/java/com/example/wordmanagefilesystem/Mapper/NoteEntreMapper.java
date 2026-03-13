package com.example.wordmanagefilesystem.Mapper;

import com.example.wordmanagefilesystem.Pojo.Note.NoteWordBody;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteEntreMapper {

    //分页查询某用户，某单词本，所有单词
    @Select("select * from note_word where user_id = #{userId} and note_id = #{noteId} limit #{start} , 9")
    List<NoteWordBody> getNoteWordLimit(@Param("userId") Integer userId
            , @Param("noteId") Integer noteId , @Param("start") Integer start);

    //添加单词本单词（指定用户，本）
    @Insert("INSERT INTO note_word (user_id, note_id, word, meaning, part_of_speech" +
            ",belong_grade, similar_word, phrase, belong ) VALUES (#{userId}, #{noteId}," +
            " #{word}, #{meaning}, #{partOfSpeech},#{belongGrade}, #{similarWord}, #{phrase}, #{belong})")
    Integer addNoteWord( @Param("userId") Integer userId, @Param("noteId") Integer noteId, @Param("word") String word,
                         @Param("meaning") String meaning, @Param("partOfSpeech") String partOfSpeech,
                         @Param("belongGrade") String belongGrade, @Param("similarWord") String similarWord,
                         @Param("phrase") String phrase, @Param("belong") Integer belong
    );

    //删除指定用户，指定单词本的单词
    @Delete("delete  from note_word where user_id = #{userId} and note_id = #{noteId} and id = #{wordId}")
    Integer deleteNoteWord(@Param("userId") Integer userId , @Param("noteId") Integer noteId , @Param("wordId") Integer wordId);

    //修改指定单词本，指定用户的单词
    @Update("update note_word set word = #{word} , meaning = #{meaning} , part_of_speech = #{partOfSpeech}\n" +
            ", belong_grade = #{belongGrade} , similar_word = #{similarWord} , phrase = #{phrase}, belong = #{belong} " +
            "where user_id = #{userId} and note_id = #{noteId} and id = #{wordId}")
    Integer updateNoteWord(@Param("word") String word, @Param("meaning") String meaning,
                           @Param("partOfSpeech") String partOfSpeech, @Param("belongGrade") String belongGrade,
                           @Param("similarWord") String similarWord, @Param("phrase") String phrase,
                           @Param("belong") Integer belong, @Param("userId") Integer userId, @Param("noteId") Integer noteId,
                           @Param("wordId") Integer wordId
    );

    //模糊查询：根据单词
    @Select("select * from note_word where user_id = #{userId} and note_id = #{noteId} and word " +
            "like concat('%',#{word},'%') limit #{start} , 9")
    List<NoteWordBody> likeQueryByWord(@Param("userId") Integer userId, @Param("noteId") Integer noteId
            , @Param("word") String word ,@Param("start") Integer start);

    //模糊查询：根据释义
    @Select("select * from note_word where user_id = #{userId} and note_id = #{noteId} and meaning " +
            "like concat('%',#{meaning},'%') limit #{start} , 9")
    List<NoteWordBody> likeQueryByMeaning(@Param("userId") Integer userId, @Param("noteId") Integer noteId
            , @Param("meaning") String meaning , @Param("start") Integer start);

}
