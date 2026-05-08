package com.human.middle.service;
// Request DTO -> Entity -> DAO
// Entity -> Response DTO -> Controller

import com.human.middle.dao.MemberDao;
import com.human.middle.dto.Member;
import com.human.middle.dto.request.LoginReq;
import com.human.middle.dto.request.MemberRegReq;
import com.human.middle.dto.response.MemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean register(MemberRegReq req) {
        if (memberDao.isDuplicate(req.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        Member member = req.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberDao.save(member);

    }
    // 로그인
    public boolean login(LoginReq req) {
        Member member = memberDao.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        return passwordEncoder.matches(
                req.getPassword(),      // 사용자가 입력한 평문
                member.getPassword());  // DB에 저장된 BCrypt 해시값
    }
    // 전체 회원 조회
    public List<Member> findAll() {
        return memberDao.findAll();
    }

}
