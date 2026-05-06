package com.human.middle.dao;

import com.human.middle.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// 실제 Database와 연결되는 부분
@Repository   // spring bean container에 등록
@RequiredArgsConstructor  // final 필드의 생성자를 자동으로 만들어 줌
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;  // 생성자를 통한 의존성 주입

    // query(sql, rowMapper) : SELECT 다건 조회
    // queryForObject(sql, rowMapper, args) : SELECT 단건 조회
    // update(sql, args) : INSERT, UPDATE, DELETE

    // 회원 가입
    public boolean save(Member member) {
        String sql = "INSERT INTO member (username, password, nickname) VALUES (?, ?, ?)";
        int rst = jdbcTemplate.update(sql, member.getUsername(), member.getPassword(), member.getNickname());
        return rst > 0;
    }
    // 회원 중복 여부 확인
    public boolean isDuplicate(String username) {
        String sql = "SELECT COUNT(*) FROM member WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count > 0;
    }
}
