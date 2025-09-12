package com.sinse.apiapp.model.board;

import com.sinse.apiapp.domain.Board;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BoardService {
    public List<Board> selectAll();
    //컬럼만 언더바, java안에서는 camelCase
    public Board selectOne(int boardId);
    public void register(Board board);
    public void update(Board board);
    public void delete(int boardId);
}
