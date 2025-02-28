package com.example.practice.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.practice.dao.BoardDAO;
import com.example.practice.dto.BoardDTO;
import com.example.practice.entity.Board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	
	@Autowired
	BoardDAO dao;
	//new로 객체를 생성하면, @Autowired로 설정된 bean 객체는 null로 설정된다.
	//BoardDAO dao = new BoardDAO(); 
	
	// 목록보기
	@GetMapping("/board/boardList")
	public String boardList(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		// 1) pg 저장
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기 => 목록 수 : 5개
		int endNum = pg * 5;
		int startNum = endNum - 4;

		// BoardDAO dao = new BoardDAO();
		List<Board> list = dao.boardList(startNum, endNum);

		// 3) 페이징 데이터 => 3블럭
		int totalA = dao.getCount(); // 총 데이터 수
		int totalP = (totalA + 4) / 5; // 총 페이지 수

		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP)
			endPage = totalP;

		// 2. 데이터 공유
		// 세션 데이터 공유
		HttpSession session = request.getSession();
		model.addAttribute("memId", session.getAttribute("memId"));

		model.addAttribute("pg", pg);
		model.addAttribute("list", list);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		// 3. view 파일명 리턴
		return "/board/boardList"; // /board/boardList.html
	}

	@GetMapping("/board/boardWriteForm")
	public String boardWriteForm() {
		// 1. 데이터 처리
		// 2. 데이터 공유
		// 3. view 처리 파일명 리턴
		return "/board/boardWriteForm";
	}
	
	@PostMapping("/board/boardWrite")
	public String boardWrite(Model model, BoardDTO dto, HttpServletRequest request) {
		// 1. 데이터 처리
		// 한글 인코딩 설정
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		dto.setId((String) session.getAttribute("memId"));
		dto.setName((String) session.getAttribute("memName"));
		dto.setLogtime(new Date());
		
		System.out.println(dto.toString());
		// db
		Board board = dao.boardWrite(dto);
		System.out.println(board.toString());
		
		// 2. 데이터 공유
		model.addAttribute("board", board);
		// 3. view 처리 파일명 리턴
		return "board/boardWrite";  
	}	
}
