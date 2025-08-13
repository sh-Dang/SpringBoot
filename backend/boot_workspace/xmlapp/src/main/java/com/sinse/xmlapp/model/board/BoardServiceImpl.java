package com.sinse.xmlapp.model.board;

import com.sinse.xmlapp.domain.Board;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardDAO boardDAO;

    public void BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    @Override
    public List<Board> selectAll() {
        return boardDAO.selectAll();
    }
    @Override
    public Board select(int board_id) {
        return boardDAO.select(board_id);
    }
    @Override
    public void insert(Board board) {
        boardDAO.insert(board);
    }
    @Override
    public void update(Board board) {
        boardDAO.update(board);
    }
    @Override
    public void delete(int board_id) {
        boardDAO.delete(board_id);
    }
}
