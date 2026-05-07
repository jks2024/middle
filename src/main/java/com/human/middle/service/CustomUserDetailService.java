package com.human.middle.service;

import com.human.middle.dao.MemberDao;
import com.human.middle.dto.CustomUser;
import com.human.middle.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberDao memberDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + username));

        // User.builder() 대신 CustomUser를 생성하여 리턴
        return new CustomUser(
                member.getId(), // DB에서 가져온 PK값
                member.getUsername(),
                member.getPassword(),
                org.springframework.security.core.authority.AuthorityUtils.createAuthorityList(member.getRole())
        );
    }
}
