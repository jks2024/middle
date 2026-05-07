package com.human.middle.dto.request;

import com.human.middle.dto.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Getter, Setter, toString Hash, Equals
@NoArgsConstructor // 매개변수가 없는 기본 생성자 추가
public class BoardWriteReq {
    @NotBlank(message = "제목을 입력 하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    // Entity 변환 (memberId가 포함되어야 함)
    public Board toEntity(Long memberId) {
        Board board = new Board();
        board.setMemberId(memberId);
        board.setTitle(title);
        board.setContent(content);
        return board;
    }
}
