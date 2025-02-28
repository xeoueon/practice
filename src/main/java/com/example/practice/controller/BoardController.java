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
	
	@GetMapping("/board/boardView")
	public String boardView(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		dao.updateHit(seq);					// 조회수 증가
		Board board = dao.boardView(seq); 	// 1줄 데이터 가져오기
		
		// 2. 데이터 공유
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		model.addAttribute("board", board);
		// 글쓴이 인지 확인하기 위해서 id도 공유시킨다.
		HttpSession session = request.getSession();
		model.addAttribute("memId", session.getAttribute("memId"));
		// 3. view 처리 파일명 리턴
		return "/board/boardView";
	}
	
	@GetMapping("/board/boardDelete")
	public String boardDelete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		// db
		boolean result = dao.boardDelete(seq); 	
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("result", result);
		// 3. view 처리 파일명 리턴
		return "/board/boardDelete";
	}
	
	@GetMapping("/board/boardModifyForm")
	public String boardModifyForm(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		// db
		Board board = dao.boardView(seq); 
		
		// 2. 데이터 공유
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		model.addAttribute("board", board);
		// 3. view 처리 파일명 리턴
		return "/board/boardModifyForm";
	}
	
	@PostMapping("/board/boardModify")
	public String boardModify(Model model, BoardDTO dto, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		System.out.println(dto.toString());
		// db
		boolean result = dao.boardModify(dto);
		
		// 2. 데이터 공유
		model.addAttribute("seq", dto.getSeq());
		model.addAttribute("pg", pg);
		model.addAttribute("result", result);
		// 3. view 처리 파일명 리턴
		return "board/boardModify";  
	}
}
