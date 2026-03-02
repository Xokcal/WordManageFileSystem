package com.example.wordmanagefilesystem.Service.Implement;

import com.example.wordmanagefilesystem.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Mapper.NoteMapper;
import com.example.wordmanagefilesystem.Pojo.Note.NoteBookBody;
import com.example.wordmanagefilesystem.Service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class NoteImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;

    private static final String ADD_NOTEBOOK_SQL_ERROR = "添加单词本，添加sql注入失败！用户";
    private static final String ADD_NOTEBOOK_USERID_NULL = "添加单词本，userId为null用户：";
    private static final String QUERY_NOTEBOOK_USERID_NULL = "查询所有笔记本，userId为null用户：";
    private static final String QUERY_NOTEBOOK_SQL_ERROR = "查询所有笔记本，查询sql数据失败！用户";
    private static final long ADD_NOTEBOOK_DEFAULT_WORD_COUNT = 0 ;
    private static final Integer ADD_NOTEBOOK_DEFAULT_ACCURACY_AVG = 0 ;

    //添加单词本
    @Override
    public Integer addNoteBook(String bookName , Integer userId){
        if (CheckValidUtil.isNull(userId)){
            log.error(ADD_NOTEBOOK_USERID_NULL , userId);
            throw new BusinessExcept(ADD_NOTEBOOK_USERID_NULL + userId , 400);
        }
        Integer integer = noteMapper.addNoteBook(userId, bookName
                , LocalDate.now(), ADD_NOTEBOOK_DEFAULT_WORD_COUNT
                , ADD_NOTEBOOK_DEFAULT_ACCURACY_AVG);
        if (integer <= 0){
            throw new BusinessExcept(ADD_NOTEBOOK_SQL_ERROR + userId , 400);
        }
        return integer;
    }

    //查询所有笔记本
    private List<NoteBookBody> getAllNoteBook(Integer userId){
        if (CheckValidUtil.isNull(userId)){
            log.error(QUERY_NOTEBOOK_USERID_NULL , userId);
            throw new BusinessExcept(QUERY_NOTEBOOK_USERID_NULL + userId , 400);
        }
        List<NoteBookBody> allNoteBookByUserId = noteMapper.getAllNoteBookByUserId(userId);
        if (CheckValidUtil.isValid(allNoteBookByUserId)){
            throw new BusinessExcept(QUERY_NOTEBOOK_SQL_ERROR + userId , 400);
        }
        return allNoteBookByUserId;
    }

    @Override
    //查询笔记本
    public List<NoteBookBody> searchNoteBookOrAll(String bookName , Integer userId){
        if (CheckValidUtil.isNull(userId)){
            throw new BusinessExcept(QUERY_NOTEBOOK_USERID_NULL + userId , 400);
        }
        if (bookName.isEmpty() || bookName == null){
            return getAllNoteBook(userId);
        }
        List<NoteBookBody> noteBookBodies = noteMapper.searchNoteBook(bookName, userId);
        if (CheckValidUtil.isValid(noteBookBodies)){
            throw new BusinessExcept(QUERY_NOTEBOOK_SQL_ERROR + userId , 400);
        }
        return noteBookBodies;
    }
}
