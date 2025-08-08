package com.sinse.practice.model.board;

import com.sinse.practice.domain.Board;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private BoardDAO boardDAO;
    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    @Override
    public List<Board> selectAll() {
        return List.of();
    }

    @Override
    public Board select(int board_id) {
        return null;
    }

    @Override
    public void insert(Board board) {
        boardDAO.insert(board);
    }

    @Override
    public void update(Board board) {

    }

    @Override
    public void delete(int board_id) {

    }
}
