package com.example.practice.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data  		// toString, getter, setter 설정
@NoArgsConstructor 		// 디폴트 생성자 설정
@AllArgsConstructor		// 모든 멤버변수를 가진 생성자 설정
public class Board {
	@Id		// PK 설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
				generator = "BOARD_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "BOARD_SEQUENCE_GENERATOR",
				sequenceName = "seq_board", initialValue = 1, allocationSize = 1)	
	private int seq;          		// 글번호
    private String id;  	 		// 아이디
    private String name;   			// 이름
    private String subject; 		// 제목
    private String content; 		// 내용
    private int hit;       			// 조회수
    @Temporal(TemporalType.DATE)	// 년월일 저장 설정
    private Date logtime;    		// 작성일
}
