package com.example.wordmanagefilesystem.Controller;

import com.example.wordmanagefilesystem.Pojo.Note.NoteBookBody;
import com.example.wordmanagefilesystem.Pojo.Result;
import com.example.wordmanagefilesystem.Service.Implement.NoteImpl;
import com.example.wordmanagefilesystem.Service.NoteService;
import com.example.wordmanagefilesystem.Tool.JWTTool;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteImpl noteImpl;

    @Autowired
    private JWTTool jwtTool;

    @PostMapping()
    public Result addNoteBook(@RequestParam("bookName") String bookName
            , @RequestHeader("userToken") String token){
        Claims claims = jwtTool.parseToken(token);
        Integer r = noteImpl.addNoteBook(bookName, (Integer) claims.get("id"));
        return new Result().noteSuccess(r);
    }

    @GetMapping()
    public Result searchNoteBookOrGetAll(@RequestParam("bookName") String bookName
            , @RequestHeader("userToken") String token){
        Claims claims = jwtTool.parseToken(token);
        List<NoteBookBody> r = noteImpl.searchNoteBookOrAll(bookName, (Integer) claims.get("id"));
        return new Result().noteSuccess(r);
    }


}
