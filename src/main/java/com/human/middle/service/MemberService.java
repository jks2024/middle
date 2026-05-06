package com.human.middle.service;
// Request DTO -> Entity -> DAO
// Entity -> Response DTO -> Controller

import com.human.middle.dao.MemberDao;
import com.human.middle.dto.Member;
import com.human.middle.dto.request.MemberRegReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(MemberRegReq req) {
        if (memberDao.isDuplicate(req.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        Member member = req.toEntity();
        //member.setPassword(passwordEncoder.encode(member.getPassword()));
        boolean saved = memberDao.save(member);
        if (!saved) throw new RuntimeException("회원 가입 중 오류가 발생했습니다.");
    }
}
