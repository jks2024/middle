package com.human.middle.dao;

import com.human.middle.dto.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor  // 생성자를 통한 의존선 주입을 간편하게
public class BoardDao {
    private final JdbcTemplate jdbcTemplate;

    // 게시글 작성
    public boolean save(Board board) {

    }

    // 게시글 전체 목록 조회
    public List<Board> findAll() {

    }

    // 게시글 상제 조회 (boardId)
    public Optional<Board> findById(Long id) {}


    // 게시글 수정 (boardId, 제목, 내용)

    // 게시글 삭제 (boardId)

    // 조회수 증가
    public boolean increaseViewCount(Long id) {

    }

}
