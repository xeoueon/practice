package com.example.practice.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.practice.dto.BoardDTO;
import com.example.practice.entity.Board;
import com.example.practice.repository.BoardRepository;



@Repository
public class BoardDAO {
	@Autowired
	BoardRepository boardRepository;
	
	// 총 글수
	public int getCount() {
		return (int) boardRepository.count();
	}

	// 인덱스뷰 목록
	public List<Board> boardList(int startNum, int endNum) {
		return boardRepository.findByStartnumAndEndnum(startNum, endNum);
	}
	
	// 글 저장
	public Board boardWrite(BoardDTO dto) {
		// dto를 entity로 변경
		Board board = dto.toEntity();
		// 저장 및 리턴
		return boardRepository.save(board);
	}	
	
	// 상세보기
	public Board boardView(int seq) {
		return boardRepository.findById(seq).orElse(null);
	}
	
	// 조회수 증가
	public Board updateHit(int seq) {
		// 1. 기존 데이터 가져오기
		Board board = boardRepository.findById(seq).orElse(null);
		
		if(board != null) {
			// 2. 수정 : 조회수 증가
			board.setHit(board.getHit() + 1);
			// 3. 저장
			return boardRepository.save(board);
		}
		return null;
	}
	
	// 삭제하기
	public boolean boardDelete(int seq) {
		// 1. 기존 데이터 가져오기
		Board board = boardRepository.findById(seq).orElse(null);
		boolean result = false;
		
		if(board != null) {
			// 2. 삭제 
			boardRepository.delete(board); 
	        // 3. 존재 검사
			if(!boardRepository.existsById(seq)) {
				result = true;
	        }
		}
		return result;
	}
	
	// 수정하기
	public boolean boardModify(BoardDTO dto) {
		// 1. 기존 데이터 가져오기
		Board board = boardRepository.findById(dto.getSeq()).orElse(null);
		boolean result = false;
		if(board != null) { 	// 데이터가 존재하면
			// 2. 수정
			board.setLogtime(dto.getLogtime());			// 기존 등록일 유지
			board.setLogtime(new Date());				// 수정일 저장
			board.setSubject(dto.getSubject());
			board.setContent(dto.getContent());
			// 3. 저장
			Board board_result = boardRepository.save(board);
			if(board_result != null) {
				result = true;
			}
		}
		return result;
	}
}
