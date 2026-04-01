package com.example.wordmanagefilesystem.Service;

import com.example.wordmanagefilesystem.Pojo.Note.NoteAndNoteWordCountVO;
import com.example.wordmanagefilesystem.Pojo.Note.NoteBookBody;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NoteService {

    public Integer addNoteBook(String bookName , Integer userId);
    public List<NoteBookBody> searchNoteBookOrAll(String bookName , Integer userId);
    public NoteAndNoteWordCountVO noteAndNoteWordCountView(Integer userId);
}
