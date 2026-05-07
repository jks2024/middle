package com.human.middle.controller;

import com.human.middle.dto.request.LoginReq;
import com.human.middle.dto.request.MemberRegReq;
import com.human.middle.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<?> register (@Valid @RequestBody MemberRegReq req) {
        boolean success = memberService.register(req);
        if (success) return ResponseEntity.ok("회원 가입 성공");
        else return ResponseEntity.ok("회원 가입 실패");
    }

    // POST /auth/login → SecurityConfig의 loginProcessingUrl이 처리
    // ↓ GET만 남김 (Thymeleaf 사용 시 로그인 페이지 반환용)
    @GetMapping("/login")
    public ResponseEntity<?> loginPage() {
        return ResponseEntity.ok("로그인 페이지");
    }

    // 회원 전체 조회
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    // username으로 개별 회원 조회


}
