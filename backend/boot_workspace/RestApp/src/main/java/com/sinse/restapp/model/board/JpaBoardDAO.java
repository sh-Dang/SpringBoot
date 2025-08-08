package com.sinse.restapp.model.board;

import com.sinse.restapp.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("jpaBoardDAO")
public class JpaBoardDAO implements BoardDAO{

    //하이버네이트의 경우 Session 객체가 쿼리 수행객체
    //JPA에서는 EntityManager를 쓴다. 최사우이인터페이스 이므로, 하이버네티스를 사용할 경우에도
    //EntityManager를 쓸 수 있다.
    @PersistenceContext //JPA의 EntityManager를 자동으로 주입받음
    private EntityManager em;

    @Override
    public List selectAll() {
        return em.createQuery("select b from Board b").getResultList();
    }

    @Override
    public Board select(int board_id) {
        return em.find(Board.class, board_id);
    }

    @Override
    public void insert(Board board) {
        em.persist(board);
    }

    @Override
    public Board update(Board board) {
        return em.merge(board);
    }

    @Override
    public void delete(int board_id) {
        //삭제시 실제로 존재하는지 먼저 체크하므로 삭제하는 것이 안전
        Board board = em.find(Board.class, board_id=1);
        if(board!=null) {
            em.remove(board);
        }
    }
}
