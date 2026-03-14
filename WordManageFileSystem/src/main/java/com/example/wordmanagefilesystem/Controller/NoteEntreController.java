package com.example.wordmanagefilesystem.Controller;

import com.example.wordmanagefilesystem.Pojo.Note.NoteWordBody;
import com.example.wordmanagefilesystem.Pojo.Result;
import com.example.wordmanagefilesystem.Service.Implement.NoteEntreImpl;
import com.example.wordmanagefilesystem.Tool.JWTTool;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/noteEntre")
public class NoteEntreController {

    @Autowired
    private NoteEntreImpl noteEntreImpl;

    @Autowired
    private JWTTool jwtTool;

    //查询单词
    @GetMapping("/select")
    Result getNoteWord(@RequestHeader("userToken") String token
            , @RequestParam("noteId") Integer noteId , @RequestParam("page") Integer page){
        Claims claims = jwtTool.parseToken(token);
        List<NoteWordBody> words = noteEntreImpl.getNoteWordLimit((Integer) claims.get("id"), noteId, page);
        return new Result().successNote(words);
    }

    //添加单词
    @PostMapping("/insert")
    Result addNoteWord(@RequestHeader("userToken") String token
            , @RequestParam("noteId") Integer noteId , @RequestBody NoteWordBody w){
        Claims claims = jwtTool.parseToken(token);
        String s = noteEntreImpl.addNoteWord((Integer) claims.get("id"), noteId, w);
        return new Result().successNote(s);
    }

    //删除单词
    @DeleteMapping("/delete")
    Result deleteNoteWord(@RequestHeader("userToken") String token
            , @RequestParam("noteId") Integer noteId , @RequestParam("wordId") Integer wordId){
        Claims claims = jwtTool.parseToken(token);
        String s = noteEntreImpl.deleteNoteWord((Integer) claims.get("id"), noteId, wordId);
        return new Result().successNote(s);
    }

    //更新单词
    @PutMapping("/update")
    Result updateNoteWord(@RequestHeader("userToken") String token
            , @RequestParam("noteId") Integer noteId , @RequestBody NoteWordBody w
            , @RequestParam("wordId") Integer wordId){
        Claims claims = jwtTool.parseToken(token);
        String s = noteEntreImpl.updateNoteWord((Integer) claims.get("id"), noteId, w , wordId);
        return new Result().successNote(s);
    }

    //模糊查询：根据单词
    @GetMapping("/query-by-word")
    Result likeQueryByWord(@RequestHeader("userToken") String token , @RequestParam(value = "noteId") Integer noteId
            ,@RequestParam("word") String word , @RequestParam("page") Integer page){
        Claims claims = jwtTool.parseToken(token);
        List<NoteWordBody> words = noteEntreImpl.likeQueryByWord((Integer) claims.get("id"), noteId, word, page);
        return new Result().successNote(words);
    }

    @GetMapping("/query-by-meaning")
    Result likeQueryByMeaning(@RequestHeader("userToken") String token , @RequestParam("noteId") Integer noteId
            ,@RequestParam("meaning") String meaning , @RequestParam("page") Integer page){
        Claims claims = jwtTool.parseToken(token);
        List<NoteWordBody> words = noteEntreImpl.likeQueryByMeaning((Integer) claims.get("id"), noteId, meaning, page);
        return new Result().successNote(words);
    }


}
