package com.example.practice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.practice.dto.MemberDTO;
import com.example.practice.entity.Member;
import com.example.practice.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j	// log 출력
@Controller
public class LoginController {
	@Autowired
	MemberRepository memberRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		// 1. 데이터 처리
		// 2. 데이터 공유
		// 3. view 파일명 리턴
		return "/member/loginForm";		// /member/loginForm.html
	}
	
	// 브라우저로 부터 전달된 데이터는 
	// @PostMapping 설정된 함수의 매개변수의 변수로 전달되어 저장된다.
	// MemberDTO [name=null, id=hong, pwd=1234, gender=null, email1=null, 
	// email2=null, tel1=null, tel2=null, tel3=null, addr=null, logtime=null]
	@PostMapping("/login")
	public String login(MemberDTO dto, Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		System.out.println(dto.toString());
		log.info(dto.toString());
		// 1-1) id와 pwd를 조회하기
		Member member = memberRepository.findByIdAndPwd(dto.getId(), dto.getPwd());
		if(member != null) { // 존재하면
			// session에 id와 name 저장
			HttpSession session = request.getSession();
			session.setAttribute("memId", member.getId());
			session.setAttribute("memName", member.getName());
			
			// 2. 데이터 공유
			model.addAttribute("member", member);
			// 3. view 파일명 리턴
			// => html 페이지로 이동하는 것이 아니고, 요청처리를 해야하기 때문에 redirect한다.
			return "redirect:/board/boardList";
		} else {
			// 로그인 실패하면, 다시 로그인 화면으로 이동
			return "/member/loginForm";	
		}
	}
}
