package com.sinse.xmlapp.model.board;

import com.sinse.xmlapp.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//Mybatis의 xml을 직접 제어
@Mapper //이 어노테이션으로 인해 Mapper.xml과 매핑이 가능하도록 만들어줌
public interface BoardMapper {
    public List<Board> selectAll();
    public Board select(int board_id);
    public void insert(Board board);
    public void update(Board board);
    public void delete(int board_id);
}
