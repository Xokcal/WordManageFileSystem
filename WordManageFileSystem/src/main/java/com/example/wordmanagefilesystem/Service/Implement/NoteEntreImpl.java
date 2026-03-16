package com.example.wordmanagefilesystem.Service.Implement;

import com.example.wordmanagefilesystem.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Mapper.NoteEntreMapper;
import com.example.wordmanagefilesystem.Pojo.Note.NoteWordBody;
import com.example.wordmanagefilesystem.Service.Constant.NoteEntreConstant;
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

    //查询笔记本单词
    @Override
    public List<NoteWordBody> getNoteWordLimit(Integer userId, Integer noteId, Integer page) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId)
                || CheckValidUtil.isNull(page)) {
            log.warn(NoteEntreConstant.GET_WORD_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.GET_WORD_PARAM_ERROR, 400);
        }
        Integer start = (page - 1) * NoteEntreConstant.FIXED_PAGE_SIZE;
        List<NoteWordBody> words = noteEntreMapper.getNoteWordLimit(userId, noteId, start);
        if (CheckValidUtil.isValid(words))
            throw new BusinessExcept(NoteEntreConstant.GET_RETURN_RESULT_ERROR, 500);
        return words;
    }

    //添加单词
    @Override
    public String addNoteWord(Integer userId, Integer noteId, NoteWordBody w) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(w)) {
            log.warn(NoteEntreConstant.ADD_WORD_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.ADD_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.addNoteWord(userId, noteId, w.getWord(), w.getMeaning()
                , w.getPartOfSpeech(), w.getBelongGrade(), w.getSimilarWord(), w.getPhrase(), w.getBelong());
        return r > 0 ? NoteEntreConstant.
                ADD_RETURN_SET_STRING_RIGHT :
                NoteEntreConstant.ADD_RETURN_SET_STRING_ERROR;
    }

    //删除笔记本单词
    @Override
    public String deleteNoteWord(Integer userId, Integer noteId, Integer wordId) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(wordId)) {
            log.warn(NoteEntreConstant.DELETE_WORD_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.DELETE_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.deleteNoteWord(userId, noteId, wordId);
        return r > 0 ? NoteEntreConstant.
                DELETE_RETURN_SET_STRING_RIGHT :
                NoteEntreConstant.DELETE_RETURN_SET_STRING_ERROR;
    }

    //修改笔记本单词
    @Override
    public String updateNoteWord(Integer userId, Integer noteId, NoteWordBody w, Integer wordId) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(w)) {
            log.warn(NoteEntreConstant.UPDATE_WORD_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.UPDATE_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.updateNoteWord(w.getWord(), w.getMeaning(), w.getPartOfSpeech()
                , w.getBelongGrade(), w.getSimilarWord(), w.getPhrase(), w.getBelong(), userId, noteId, wordId);
        return r > 0 ? NoteEntreConstant.UPDATE_RETURN_SET_STRING_RIGHT :
                NoteEntreConstant.UPDATE_RETURN_SET_STRING_ERROR;
    }

    //模糊查询：根据单词
    @Override
    public List<NoteWordBody> likeQueryByWord(Integer userId, Integer noteId, String word, Integer page) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(word)
                || CheckValidUtil.isNull(page)) {
            log.warn(NoteEntreConstant.LIKE_QUERY_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.LIKE_QUERY_PARAM_ERROR, 400);
        }
        Integer start = (page - 1) * NoteEntreConstant.FIXED_PAGE_SIZE;
        List<NoteWordBody> words = noteEntreMapper.likeQueryByWord(userId, noteId, word, start);
        if (CheckValidUtil.isValid(words)) {
            log.warn(NoteEntreConstant.LIKE_QUERY_RESULT_ERROR);
            throw new BusinessExcept(NoteEntreConstant.
                    LIKE_QUERY_RESULT_ERROR, 500);
        }
        return words;
    }

    //模糊查询：根据释义
    @Override
    public List<NoteWordBody> likeQueryByMeaning(Integer userId, Integer noteId, String meaning, Integer page) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(meaning) ||
                CheckValidUtil.isNull(page)) {
            log.warn(NoteEntreConstant.LIKE_QUERY_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.LIKE_QUERY_PARAM_ERROR, 400);
        }
        Integer start = (page - 1) * NoteEntreConstant.FIXED_PAGE_SIZE;
        List<NoteWordBody> words = noteEntreMapper.likeQueryByMeaning(userId, noteId, meaning, start);
        if (CheckValidUtil.isValid(words)) {
            log.warn(NoteEntreConstant.LIKE_QUERY_RESULT_ERROR);
            throw new BusinessExcept(NoteEntreConstant.
                    LIKE_QUERY_RESULT_ERROR, 500);
        }
        return words;
    }


}


