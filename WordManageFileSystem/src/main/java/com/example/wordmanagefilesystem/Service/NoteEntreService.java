package com.example.wordmanagefilesystem.Service;

import com.example.wordmanagefilesystem.Pojo.Note.NoteAndNoteWordCountVO;
import com.example.wordmanagefilesystem.Pojo.Note.NoteWordBody;
import com.example.wordmanagefilesystem.Pojo.Note.NoteWordResponseBody;

import java.util.List;

public interface NoteEntreService {
    public NoteWordResponseBody getNoteWordLimit(Integer userId, Integer noteId, Integer page);
    public String addNoteWord(Integer userId , Integer noteId , NoteWordBody w);
    public String deleteNoteWord(Integer userId , Integer noteId , Integer wordId);
    public String updateNoteWord(Integer userId , Integer noteId , NoteWordBody w , Integer wordId);
    public List<NoteWordBody> likeQueryByWord(Integer userId , Integer noteId , String word , Integer page);
    public List<NoteWordBody> likeQueryByMeaning(Integer userId , Integer noteId , String word , Integer page);
    public NoteWordBody getNoteWordById(Integer userId , Integer noteId , Integer wordId);
    public Integer deleteNoteBook(Integer userId , Integer noteId , String password);
}
