package com.sinse.apiapp.controller;

import com.sinse.apiapp.domain.Board;
import com.sinse.apiapp.model.board.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
* 글등록   POST    /api/boards
* 글목록   GET     /api/boards
* 상세보기  GET     /api/boards/{boardId}
* 수정하기  PUT     /api/boards/{boardId}
* 삭제하기  DELETE  /api/boards/{boardId}
*
* */
//JSON으로 날려야하니 RestController
@RestController
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //목록
    @GetMapping("/boards")
    public List<Board> selectAll() {
        return boardService.selectAll();
    }
    //글등록
    @PostMapping("/boards")
    public ResponseEntity<Map> addBoard(@RequestBody Board board) {
        boardService.register(board);
        return ResponseEntity.ok(Map.of("result","등록성공"));
    }
    //상세보기
    @GetMapping("/boards/{boardId}")
    public Board getBoard(@PathVariable int boardId) {
        return boardService.selectOne(boardId);
    }
    //수정하기
    @PutMapping("/boards/{boardId}")
    public ResponseEntity<Map> updateBoard(@PathVariable int boardId, @RequestBody Board board) {
        boardService.update(board);
        return ResponseEntity.ok(Map.of("result","수정성공"));
    }
    //삭제하기
    @DeleteMapping("/boards/{boardId}")
    public  ResponseEntity<Map> deleteBoard(@PathVariable int boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok(Map.of("result","삭제되었습니다."));
    }


}
