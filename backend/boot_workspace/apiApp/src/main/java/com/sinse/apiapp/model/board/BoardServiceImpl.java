package com.sinse.apiapp.model.board;

import com.sinse.apiapp.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final JpaRepository<Board,Integer> jpaBoardRepository;
    public BoardServiceImpl(JpaRepository<Board,Integer> jpaBoardRepository) {
        this.jpaBoardRepository = jpaBoardRepository;
    }

    @Override
    public List<Board> selectAll() {
        return jpaBoardRepository.findAll();
    }

    @Override
    public Board selectOne(int boardId) {
        return jpaBoardRepository.findById(boardId).orElse(null);
    }

    @Override
    public void register(Board board) {
        jpaBoardRepository.save(board);
    }

    @Override
    public void update(Board board) {
        jpaBoardRepository.save(board);
    }

    @Override
    public void delete(int boardId) {
        jpaBoardRepository.deleteById(boardId);
    }
}
