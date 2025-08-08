package com.sinse.practice.model.board;

import com.sinse.practice.domain.Board;

import java.util.List;

public interface BoardService {
    public List<Board> selectAll();
    public Board select(int board_id);
    public void insert(Board board);
    public void update(Board board);
    public void delete(int board_id);

}
