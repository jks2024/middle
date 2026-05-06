package com.human.middle.dto.request;
// 회원 가입 요청

import com.human.middle.dto.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberRegReq {
    @NotBlank(message = "아이디를 입력하세요")
    private String username;
    @NotBlank(message = "비밀번호를 입력 하세요")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
    private String nickname;

    // Entity로 변환하는 메서드
    public Member toEntity() {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password);  // Service 로직에서 암호화 처리 예정
        member.setNickname(nickname);
        return member;
    }
}
