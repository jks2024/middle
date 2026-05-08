package com.human.middle.controller;

import com.human.middle.dto.request.MemberRegReq;
import com.human.middle.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        // Request DTO를 모델에 담아 폼 바이딩
        model.addAttribute("registerRequest", new MemberRegReq());
        return "auth/register";  // templates/auth/register
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") MemberRegReq request,
                           BindingResult bindingResult, RedirectAttributes ra) {
        // @Valid 검증 실패 시 폼 페이지로 되돌아감
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            memberService.register(request);
            return "redirect:/auth/login";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/register";
        }
    }
}
