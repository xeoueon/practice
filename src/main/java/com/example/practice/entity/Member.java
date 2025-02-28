package com.example.practice.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data	 // toString, getter, setter 설정
@NoArgsConstructor		// 디폴트 생성자 설정
@AllArgsConstructor		// 모든 멤버변수를 가진 생성자 설정
public class Member {
    private String name;
    @Id		// PK 설정
    private String id;
    private String pwd;
    private String gender;
    private String email1;
    private String email2;
    private String tel1;
    private String tel2;
    private String tel3;
    private String addr;
    @Temporal(TemporalType.DATE) // 년월일만 저장 설정
    private Date logtime;
}
