package com.example.wordmanagefilesystem.Service.Implement;

import com.example.wordmanagefilesystem.Common.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Mapper.NoteMapper;
import com.example.wordmanagefilesystem.Pojo.Note.NoteBookBody;
import com.example.wordmanagefilesystem.Service.Constant.NoteConstant;
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

    //添加单词本
    @Override
    public Integer addNoteBook(String bookName , Integer userId){
        if (CheckValidUtil.isNull(userId)){
            log.error(NoteConstant.ADD_NOTEBOOK_USERID_NULL , userId);
            throw new BusinessExcept(NoteConstant.ADD_NOTEBOOK_USERID_NULL + userId , 400);
        }
        Integer integer = noteMapper.addNoteBook(userId, bookName
                , LocalDate.now(), NoteConstant.ADD_NOTEBOOK_DEFAULT_WORD_COUNT
                , NoteConstant.ADD_NOTEBOOK_DEFAULT_ACCURACY_AVG);
        if (integer <= 0){
            throw new BusinessExcept(NoteConstant.ADD_NOTEBOOK_SQL_ERROR + userId , 500);
        }
        return integer;
    }

    //查询所有笔记本
    private List<NoteBookBody> getAllNoteBook(Integer userId){
        if (CheckValidUtil.isNull(userId)){
            log.error(NoteConstant.QUERY_NOTEBOOK_USERID_NULL , userId);
            throw new BusinessExcept(NoteConstant.QUERY_NOTEBOOK_USERID_NULL + userId , 400);
        }
        List<NoteBookBody> allNoteBookByUserId = noteMapper.getAllNoteBookByUserId(userId);
        if (CheckValidUtil.isValid(allNoteBookByUserId)){
            throw new BusinessExcept(NoteConstant.QUERY_NOTEBOOK_SQL_ERROR + userId , 500);
        }
        return allNoteBookByUserId;
    }

    @Override
    //查询笔记本
    public List<NoteBookBody> searchNoteBookOrAll(String bookName , Integer userId){
        if (CheckValidUtil.isNull(userId)){
            throw new BusinessExcept(NoteConstant.QUERY_NOTEBOOK_USERID_NULL + userId , 400);
        }
        if (bookName.isEmpty() || bookName == null){
            return getAllNoteBook(userId);
        }
        List<NoteBookBody> noteBookBodies = noteMapper.searchNoteBook(bookName, userId);
        if (CheckValidUtil.isValid(noteBookBodies)){
            throw new BusinessExcept(NoteConstant.QUERY_NOTEBOOK_SQL_ERROR + userId , 500);
        }
        return noteBookBodies;
    }

}
