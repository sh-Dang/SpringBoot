package com.sinse.practice.controller;

import com.sinse.practice.domain.Board;
import com.sinse.practice.model.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class TestController {
    //Autowired대신 생성자 주입
    private BoardService boardService;
    TestController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/boards")
    public ResponseEntity register(@RequestBody Board board){
        boardService.insert(board);
        return ResponseEntity.ok().build();
    }
}
