package com.example.wordmanagefilesystem.Mapper;

import com.example.wordmanagefilesystem.Pojo.Note.NoteBookBody;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface NoteMapper {

    //添加单词本
    @Insert("insert into note_book (user_id, book_name, create_time, word_count, accurcay_avg)\n" +
            "value (#{userId} , #{bookName} , #{createTime} , #{wordCount} , #{accuracyAvg})")
    Integer addNoteBook(@Param("userId") Integer userId, @Param("bookName") String bookName
            , @Param("createTime")LocalDate createTime, @Param("wordCount") long wordCount
            , @Param("accuracyAvg")Integer accuracyAvg);

    //查询所有单词本
    @Select("select * from note_book where user_id = #{userId}")
    List<NoteBookBody> getAllNoteBookByUserId(@Param("userId") Integer userId);

    //查询单词本
    @Select("select * from note_book where book_name like concat('%',#{bookName},'%')and user_id = #{userId}")
    List<NoteBookBody> searchNoteBook(@Param("bookName") String bookName , @Param("userId") Integer userId);

    /*笔记本与词汇数量统计*/

    //笔记本统计
    @Select("select book_name from note_book where user_id = #{userId} group by book_name")
    List<String> reportBookName(@Param("userId") Integer userId);

    //词汇数量统计
    @Select("select max(word_count) from note_book where user_id = #{userId} group by book_name")
    List<Integer> reportNoteWordCount(@Param("userId") Integer userId);
}
