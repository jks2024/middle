package com.human.middle.service;

import com.human.middle.dao.BoardDao;
import com.human.middle.dto.Board;
import com.human.middle.dto.CustomUser;
import com.human.middle.dto.request.BoardWriteReq;
import com.human.middle.dto.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardDao boardDao;

    // 게시글 작성
    @Transactional
    public boolean writeBoard(BoardWriteReq req) {
        // 1. 시큐리티 세션(Context)에서 인증된 사용자 정보(Principal)를 꺼냄
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUser) {
            // 2. 우리가 만든 CustomUser로 형변환 후 memberId 추출
            Long memberId = ((CustomUser) principal).getMemberId();

            // 3. 추출한 memberId를 toEntity 메서드에 전달
            return boardDao.save(req.toEntity(memberId));
        }

        // 로그인 정보가 없는 경우 (익명 사용자 등)
        throw new RuntimeException("로그인이 필요합니다.");
    }

    // 전체 목록 조회
    public List<BoardResponse> getBoards() {
        List<Board> boards = boardDao.findAll();
        List<BoardResponse> responses = new ArrayList<>();
        for (Board board : boards) {
            responses.add(BoardResponse.of(board));
        }
        return responses;
    }
    // 게시글 상세 조회
    public BoardResponse getBoardById(Long id) {
        Board board = boardDao.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return BoardResponse.of(board);
    }

    // 게시글 삭제
    @Transactional
    public boolean deleteBoard(Long id) {
        return boardDao.delete(id);
    }

    // 게시글 수정
    @Transactional
    public boolean updateBoard(BoardWriteReq req, Long id) {
        return boardDao.update(id, req.getTitle(), req.getContent());
    }

}
