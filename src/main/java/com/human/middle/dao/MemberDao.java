package com.human.middle.dao;

import com.human.middle.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    // username으로 회원 정보 조회
    public Optional<Member> findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        try {
            // queryForObject: 결과가 0건이면 EmptyResultDataAccessException 발생
            // 결과가 2건 이상이면 IncorrectResultSizeDataAccessException 발생
            Member member = jdbcTemplate.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(Member.class), username);

            // member가 null이면 Optional.empty(), 있으면 Optional.of(member) 반환
            return Optional.ofNullable(member);

        } catch (EmptyResultDataAccessException e) {
            // 조회 결과가 없는 건 오류가 아니므로 Optional.empty() 반환
            return Optional.empty();
        }
    }
    // username와 password 일치 여부 확인
    public boolean isMatch(String username, String password) {
        String sql = "SELECT COUNT(*) FROM member WHERE username = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count > 0;
    }
    // 전체 회원 조회
    public List<Member> findAll() {
        String sql = "SELECT * FROM member ORDER BY id DESC";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Member.class));
    }

}
