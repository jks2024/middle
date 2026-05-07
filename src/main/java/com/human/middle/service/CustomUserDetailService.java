package com.human.middle.service;

import com.human.middle.dao.MemberDao;
import com.human.middle.dto.CustomUser;
import com.human.middle.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberDao memberDao;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // DB에서 회원 조회
        Member member = memberDao.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("존재하지 않는 아이디: " + username));

        // 권한 설정
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(member.getRole()));

        // CustomUser로 반환 → memberId가 세션에 함께 저장됨
        return new CustomUser(member, authorities);
    }
}
