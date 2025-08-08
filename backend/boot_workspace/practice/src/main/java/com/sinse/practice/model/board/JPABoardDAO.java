package com.sinse.practice.model.board;

import com.sinse.practice.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jpaBoardDAO")
public class JPABoardDAO implements BoardDAO {

    @PersistenceContext
    private EntityManager em;//JPA SqlSession 대용

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
        em.persist(board);
    }

    @Override
    public void update(Board board) {

    }

    @Override
    public void delete(int board_id) {

    }
}
