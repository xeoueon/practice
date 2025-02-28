package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practice.entity.Member;
// JpaRepository<엔티티 클래스명, PK 자료형>
public interface MemberRepository extends JpaRepository<Member, String> {
	// 아이디와 비밀번호로 조회
	Member findByIdAndPwd(String id, String pwd);
}
