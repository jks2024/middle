package com.human.middle.dao;

import com.human.middle.dto.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "INSERT INTO board (title, content, member_id) VALUES (?, ?, ?)";
        int rst = jdbcTemplate.update(sql,
                board.getTitle(), board.getContent(), board.getMemberId());
        return rst > 0;
    }

    // 게시글 전체 목록 조회
    public List<Board> findAll() {
        String sql =
                "SELECT b.id, b.title, b.content, b.member_id, " +
                        "       b.view_count, b.created_at, m.nickname AS writer " +
                        "FROM board b " +
                        "JOIN member m ON b.member_id = m.id " +
                        "ORDER BY b.id DESC";
        return jdbcTemplate.query(sql, (rs, n) -> {
            Board b = new Board();
            b.setId(rs.getLong("id"));
            b.setTitle(rs.getString("title"));
            b.setContent(rs.getString("content"));
            b.setMemberId(rs.getLong("member_id"));
            b.setWriter(rs.getString("writer"));
            b.setViewCount(rs.getInt("view_count"));
            b.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return b;
        });

    }

    // 게시글 상세 조회 (boardId)
    public Optional<Board> findById(Long id) {
        String sql =
                "SELECT b.*, m.nickname AS writer " +
                        "FROM board b " +
                        "JOIN member m ON b.member_id = m.id " +
                        "WHERE b.id = ?";
        try {
            Board b = jdbcTemplate.queryForObject(sql, (rs, n) -> {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setMemberId(rs.getLong("member_id"));
                board.setWriter(rs.getString("writer"));
                board.setViewCount(rs.getInt("view_count"));
                board.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return board;
            }, id);
            return Optional.ofNullable(b);
        } catch (EmptyResultDataAccessException e) {
            // 조회 결과가 없으면 Optional.empty() 반환
            return Optional.empty();
        }
    }


    // 게시글 수정 (boardId, 제목, 내용)
    public boolean update(Long id, String title, String content) {
        String sql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
        int rst = jdbcTemplate.update(sql, title, content, id);
        return rst > 0;
    }

    // 게시글 삭제 (boardId)
    public boolean delete(Long id) {
        String sql = "DELETE FROM board WHERE id = ?";
        int rst = jdbcTemplate.update(sql, id);
        return rst > 0;
    }

    // 조회수 증가
    public boolean increaseViewCount(Long id) {
        String sql = "UPDATE board SET view_count = view_count + 1 WHERE id = ?";
        int rst = jdbcTemplate.update(sql, id);
        return rst > 0;
    }
}
