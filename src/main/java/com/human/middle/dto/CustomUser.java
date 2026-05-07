package com.human.middle.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    private final Long memberId; // PK를 저장할 필드

    public CustomUser(Long memberId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }
}