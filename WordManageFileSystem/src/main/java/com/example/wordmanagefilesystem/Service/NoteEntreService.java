package com.example.wordmanagefilesystem.Service;

import com.example.wordmanagefilesystem.Pojo.Note.NoteWordBody;

import java.util.List;

public interface NoteEntreService {
    public List<NoteWordBody> getNoteWordLimit(Integer userId , Integer noteId , Integer page);
    public String addNoteWord(Integer userId , Integer noteId , NoteWordBody w);
    public String deleteNoteWord(Integer userId , Integer noteId , Integer wordId);
    public String updateNoteWord(Integer userId , Integer noteId , NoteWordBody w , Integer wordId);
    public List<NoteWordBody> likeQueryByWord(Integer userId , Integer noteId , String word , Integer page);
    public List<NoteWordBody> likeQueryByMeaning(Integer userId , Integer noteId , String word , Integer page);
}
