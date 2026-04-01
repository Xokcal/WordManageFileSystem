package com.example.wordmanagefilesystem.Pojo.Note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteWordResponseBody {
    private Integer total;
    private Integer maxPage;
    private List<NoteWordBody> words;

}
