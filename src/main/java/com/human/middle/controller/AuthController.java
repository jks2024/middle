package com.human.middle.controller;

import com.human.middle.dto.request.MemberRegReq;
import com.human.middle.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<?> register (@Valid @RequestBody MemberRegReq req) {
        memberService.register(req);
        return ResponseEntity.ok("회원 가입 성공");
    }

}
