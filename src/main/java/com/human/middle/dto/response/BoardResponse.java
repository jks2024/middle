package com.human.middle.dto.response;

import com.human.middle.dto.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor  // 매개변수가 없는 생성자
@AllArgsConstructor // 매개변수가 전부 있는 생성자
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private String writer;   // JOIN해서 가져와야 함
    private int  viewCount;
    private LocalDateTime createAt;

    // Entity -> Response 변환
    public static BoardResponse of(Board board) {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setId(board.getId());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setContent(board.getContent());
        boardResponse.setWriter(board.getWriter());
        boardResponse.setViewCount(board.getViewCount());
        boardResponse.setCreateAt(board.getCreateAt());
        return boardResponse;
    }
}

