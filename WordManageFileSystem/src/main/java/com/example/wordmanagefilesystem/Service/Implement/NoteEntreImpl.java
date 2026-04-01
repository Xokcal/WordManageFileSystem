package com.example.wordmanagefilesystem.Service.Implement;

import com.example.wordmanagefilesystem.Common.Except.BusinessExcept;
import com.example.wordmanagefilesystem.Mapper.NoteEntreMapper;
import com.example.wordmanagefilesystem.Pojo.Note.NoteWordBody;
import com.example.wordmanagefilesystem.Pojo.Note.NoteWordResponseBody;
import com.example.wordmanagefilesystem.Service.Constant.NoteEntreConstant;
import com.example.wordmanagefilesystem.Service.NoteEntreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class NoteEntreImpl implements NoteEntreService {

    @Autowired
    private NoteEntreMapper noteEntreMapper;

    //查询笔记本单词
    @Override
    public NoteWordResponseBody getNoteWordLimit(Integer userId, Integer noteId, Integer page) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId)
                || CheckValidUtil.isNull(page)) {
            log.warn(NoteEntreConstant.GET_WORD_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.GET_WORD_PARAM_ERROR, 400);
        }
        Integer total = noteEntreMapper.getNoteWordCount(noteId, userId);
        int maxPage = (total / 9) + 1;
        Integer start = (page - 1) * NoteEntreConstant.FIXED_PAGE_SIZE;
        List<NoteWordBody> words = noteEntreMapper.getNoteWordLimit(userId, noteId, start);
        if (CheckValidUtil.isValid(words))
            throw new BusinessExcept(NoteEntreConstant.GET_RETURN_RESULT_ERROR, 500);
        return new NoteWordResponseBody(total, maxPage, words);
    }

    //添加单词本单词
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addNoteWord(Integer userId, Integer noteId, NoteWordBody w) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(w)) {
            log.warn(NoteEntreConstant.ADD_WORD_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.ADD_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.addNoteWord(userId, noteId, w.getWord(), w.getMeaning()
                , w.getPartOfSpeech(), w.getBelongGrade(), w.getSimilarWord(), w.getPhrase(), w.getBelong());
        Integer row = autoUpdateNoteBookCount(userId, noteId);
        if (row < 1){
            log.error("{}",row);
            log.warn(NoteEntreConstant.ADD_NOTEBOOK_WORD_UPDATE_COUNT_ERROR);
            throw new BusinessExcept(NoteEntreConstant.ADD_NOTEBOOK_WORD_UPDATE_COUNT_ERROR ,500);
        }
        return r > 0 ? NoteEntreConstant.ADD_RETURN_SET_STRING_RIGHT :
                NoteEntreConstant.ADD_RETURN_SET_STRING_ERROR;
    }

    //删除笔记本单词
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteNoteWord(Integer userId, Integer noteId, Integer wordId) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(wordId)) {
            log.warn(NoteEntreConstant.DELETE_WORD_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.DELETE_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.deleteNoteWord(userId, noteId, wordId);
        Integer row = autoUpdateNoteBookCount(userId, noteId);
        if (row < 1){
            log.warn(NoteEntreConstant.DELETE_NOTEBOOK_WORD_UPDATE_NOTE_COUNT_ERROR);
            throw new BusinessExcept(NoteEntreConstant.DELETE_NOTEBOOK_WORD_UPDATE_NOTE_COUNT_ERROR ,500);
        }
        return r > 0 ? NoteEntreConstant.DELETE_RETURN_SET_STRING_RIGHT :
                NoteEntreConstant.DELETE_RETURN_SET_STRING_ERROR;
    }

    //修改笔记本单词
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateNoteWord(Integer userId, Integer noteId, NoteWordBody w, Integer wordId) {
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(w)) {
            log.warn(NoteEntreConstant.UPDATE_WORD_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant.UPDATE_WORD_PARAM_ERROR, 400);
        }
        Integer r = noteEntreMapper.updateNoteWord(w.getWord(), w.getMeaning(), w.getPartOfSpeech()
                , w.getBelongGrade(), w.getSimilarWord(), w.getPhrase(), w.getBelong(), userId, noteId, wordId);
        Integer row = autoUpdateNoteBookCount(userId, noteId);
        if (row < 1){
            log.warn(NoteEntreConstant.UPDATE_NOTEBOOK_WORD_UPDATE_COUNT_ERROR);
            throw new BusinessExcept(NoteEntreConstant.UPDATE_NOTEBOOK_WORD_UPDATE_COUNT_ERROR ,500);
        }
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

    //根据id查询单词本单词
    @Override
    public NoteWordBody getNoteWordById(Integer userId , Integer noteId , Integer wordId){
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(wordId)) {
            log.warn(NoteEntreConstant.GET_NOTE_WORD_BY_ID_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant
                    .GET_NOTE_WORD_BY_ID_PARAM_ERROR ,400);
        }
        NoteWordBody word = noteEntreMapper.getNoteWordById(userId, noteId, wordId);
        if (CheckValidUtil.isNull(word)) {
            log.warn(NoteEntreConstant.GET_NOTE_WORD_BY_ID_RESULT_ERROR);
            throw new BusinessExcept(NoteEntreConstant
                    .GET_NOTE_WORD_BY_ID_RESULT_ERROR ,500);
        }
        return word;
    }

    //删除笔记本
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteNoteBook(Integer userId , Integer noteId , String password){
        if (CheckValidUtil.isNull(userId) || CheckValidUtil.isNull(noteId) || CheckValidUtil.isNull(password)) {
            log.warn(NoteEntreConstant.DELETE_NOTEBOOK_PARAM_ERROR);
            throw new BusinessExcept(NoteEntreConstant
                    .DELETE_NOTEBOOK_PARAM_ERROR ,400);
        }
        if (!password.equals(noteEntreMapper.getPassword(userId))){
            log.warn(NoteEntreConstant.DELETE_NOTEBOOK_VERIFY_ERROR);
            throw new BusinessExcept(NoteEntreConstant
                    .DELETE_NOTEBOOK_VERIFY_ERROR_EXCEPTION ,500);
        }
        Integer row = noteEntreMapper.deleteNoteBook(noteId);
        return row;
    }

    //自动更新笔记本单词总数
    private Integer autoUpdateNoteBookCount(Integer userId , Integer noteId){
        Integer noteWordCount = noteEntreMapper.getNoteWordCount(noteId, userId);
        if (CheckValidUtil.isNull(noteWordCount)){
            log.warn(NoteEntreConstant.AUTO_UPDATE_NOTEBOOK_COUNT_COUNT_ERROR);
            throw new BusinessExcept(NoteEntreConstant
                    .AUTO_UPDATE_NOTEBOOK_COUNT_COUNT_ERROR ,500);
        }
        Integer row = noteEntreMapper.updateNoteBookCount(noteWordCount, noteId);
        return row;
    }

}


