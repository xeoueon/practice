package com.example.practice.dto;

import java.util.Date;

import com.example.practice.entity.Board;

import lombok.Data;

@Data
public class BoardDTO {
	private int seq;          		// 글번호
    private String id;  	 		// 아이디
    private String name;   			// 이름
    private String subject; 		// 제목
    private String content; 		// 내용
    private int hit;       			// 조회수
    private Date logtime;    		// 작성일
    
    public Board toEntity() {
    	return new Board(seq, id, name, subject, content, hit, logtime);
    }
}
