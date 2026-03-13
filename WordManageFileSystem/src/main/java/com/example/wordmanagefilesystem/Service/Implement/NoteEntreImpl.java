package com.example.wordmanagefilesystem.Service.Implement;

import com.example.wordmanagefilesystem.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Mapper.NoteEntreMapper;
import com.example.wordmanagefilesystem.Pojo.Note.NoteWordBody;
import com.example.wordmanagefilesystem.Service.NoteEntreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoteEntreImpl implements NoteEntreService {

    @Autowired
    private NoteEntreMapper noteEntreMapper;

    private static final Integer FIXED_PAGE_SIZE = 9;
    private static final String GET_WORD_PARAM_ERROR = "笔记本查询单词参数错误";
    private static final String GET_RETURN_RESULT_ERROR = "笔记本查询方法结果错误！！";
    private static final String ADD_WORD_PARAM_ERROR = "笔记本添加单词参数错误";
    private static final String ADD_RETURN_SET_STRING_RIGHT = "笔记本单词添加成功！！";
    private static final String ADD_RETURN_SET_STRING_ERROR = "笔记本单词添加失败！！";
    private static final String DELETE_WORD_PARAM_ERROR = "笔记本删除单词参数错误";
    private static final String DELETE_RETURN_SET_STRING_RIGHT = "笔记本单词删除成功！！";
    private static final String DELETE_RETURN_SET_STRING_ERROR = "笔记本单词删除失败！！";
    private static final String UPDATE_WORD_PARAM_ERROR = "笔记本修改单词参数错误";
    private static final String UPDATE_RETURN_SET_STRING_RIGHT = "笔记本单词修改成功！！";
    private static final String UPDATE_RETURN_SET_STRING_ERROR = "笔记本单词修改失败！！";
    private static final String LIKE_QUERY_PARAM_ERROR = "笔记本模糊查询参数错误";
    private static final String LIKE_QUERY_RESULT_ERROR = "笔记本模糊查询结果错误";


    //查询笔记本单词
    @Override
    public List<NoteWordBody> getNoteWordLimit(Integer userId, Integer noteId, Integer page) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(page)) {
            log.warn(GET_WORD_PARAM_ERROR);
            throw new BusinessExcept(GET_WORD_PARAM_ERROR, 400);
        }
        Integer start = (page - 1) * FIXED_PAGE_SIZE;
        List<NoteWordBody> words = noteEntreMapper.getNoteWordLimit(userId, noteId, start);
        if (CheckValidUtil.isValid(words))
            throw new BusinessExcept(GET_RETURN_RESULT_ERROR, 400);
        return words;
    }

    //添加单词
    @Override
    public String addNoteWord(Integer userId, Integer noteId, NoteWordBody w) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(w)) {
            log.warn(ADD_WORD_PARAM_ERROR);
            throw new BusinessExcept(ADD_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.addNoteWord(userId, noteId, w.getWord(), w.getMeaning()
                , w.getPartOfSpeech(), w.getBelongGrade(), w.getSimilarWord(), w.getPhrase(), w.getBelong());
        return r > 0 ? ADD_RETURN_SET_STRING_RIGHT :
                ADD_RETURN_SET_STRING_ERROR;
    }

    //删除笔记本单词
    @Override
    public String deleteNoteWord(Integer userId, Integer noteId, Integer wordId) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(wordId)) {
            log.warn(DELETE_WORD_PARAM_ERROR);
            throw new BusinessExcept(DELETE_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.deleteNoteWord(userId, noteId, wordId);
        return r > 0 ? DELETE_RETURN_SET_STRING_RIGHT :
                DELETE_RETURN_SET_STRING_ERROR;
    }

    //修改笔记本单词
    @Override
    public String updateNoteWord(Integer userId, Integer noteId, NoteWordBody w, Integer wordId) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(w)) {
            log.warn(UPDATE_WORD_PARAM_ERROR);
            throw new BusinessExcept(UPDATE_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.updateNoteWord(w.getWord(), w.getMeaning(), w.getPartOfSpeech()
                , w.getBelongGrade(), w.getSimilarWord(), w.getPhrase(), w.getBelong(), userId, noteId, wordId);
        return r > 0 ? UPDATE_RETURN_SET_STRING_RIGHT :
                UPDATE_RETURN_SET_STRING_ERROR;
    }

    //模糊查询：根据单词
    @Override
    public List<NoteWordBody> likeQueryByWord(Integer userId, Integer noteId, String word, Integer page) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(word)
                || CheckValidUtil.isNull(page)) {
            log.warn(LIKE_QUERY_PARAM_ERROR);
            throw new BusinessExcept(LIKE_QUERY_PARAM_ERROR, 400);
        }
        Integer start = (page - 1) * FIXED_PAGE_SIZE;
        List<NoteWordBody> words = noteEntreMapper.likeQueryByWord(userId, noteId, word, start);
        if (CheckValidUtil.isValid(words)) {
            log.warn(LIKE_QUERY_RESULT_ERROR);
            throw new BusinessExcept(LIKE_QUERY_RESULT_ERROR, 400);
        }
        return words;
    }

    //模糊查询：根据释义
    @Override
    public List<NoteWordBody> likeQueryByMeaning(Integer userId, Integer noteId, String meaning, Integer page) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(meaning) ||
                CheckValidUtil.isNull(page)) {
            log.warn(LIKE_QUERY_PARAM_ERROR);
            throw new BusinessExcept(LIKE_QUERY_PARAM_ERROR, 400);
        }
        Integer start = (page - 1) * FIXED_PAGE_SIZE;
        List<NoteWordBody> words = noteEntreMapper.likeQueryByMeaning(userId, noteId, meaning, start);
        if (CheckValidUtil.isValid(words)) {
            log.warn(LIKE_QUERY_RESULT_ERROR);
            throw new BusinessExcept(LIKE_QUERY_RESULT_ERROR, 400);
        }
        return words;
    }
}
