package com.example.practice.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	
}
