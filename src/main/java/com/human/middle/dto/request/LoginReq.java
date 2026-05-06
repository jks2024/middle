package com.human.middle.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginReq {
    @NotBlank(message = "아이디를 입력하세요")
    private String username;
    @NotBlank(message = "비밀번호를 입력 하세요")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}
