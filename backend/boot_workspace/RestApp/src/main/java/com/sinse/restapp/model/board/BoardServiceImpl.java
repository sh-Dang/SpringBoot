package com.sinse.restapp.model.board;

import com.sinse.restapp.domain.Board;
import com.sinse.restapp.exception.BoardException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
    //Autowired
    private BoardDAO boardDAO;

    public BoardServiceImpl(@Qualifier("mybatisBoardDAO") BoardDAO boardDAO){
        this.boardDAO = boardDAO;
    }

    @Override
    public List selectAll() {
        return boardDAO.selectAll();
    }

    @Override
    public Board select(int board_id) {
        return boardDAO.select(board_id);
    }

    @Override
    public void insert(Board board) throws BoardException {
        try {
            boardDAO.insert(board);
            //특정DB연동 기술에 국한된 것이 이 나이
//            모든기술에 중립적이어야 하므로, 예외 객체 조차도 상위의 중립적인 예외일 수록
//                    서비스가 계층이 유연해 질 수 있다
        } catch (DataAccessException e) {
            throw new BoardException("글 등록 실패",e);
        }
    }

    @Override
    public Board update(Board board) throws BoardException {
        try {
            return boardDAO.update(board);
        } catch (DataAccessException e) {
            throw new BoardException("수정실패", e);
        }
    }

    @Override
    public void delete(int board_id) throws BoardException {
        try {
            boardDAO.delete(board_id);
        } catch (DataAccessException e) {
            throw new BoardException("삭제실패", e);
        }
    }
}
