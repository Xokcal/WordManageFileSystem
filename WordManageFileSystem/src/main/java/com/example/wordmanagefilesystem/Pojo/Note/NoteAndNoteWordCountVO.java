package com.example.wordmanagefilesystem.Pojo.Note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteAndNoteWordCountVO {
    private List<String> noteList;
    private List<Integer> noteWordCountList;
}
