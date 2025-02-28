package com.example.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.practice.entity.Board;

// JpaRepository를 상속받으면, 서버가 구동될 때, 자동적으로 bean 객체가 생성된다.
public interface BoardRepository extends JpaRepository<Board, Integer> {
	// 목록 조회 : 인덱스 뷰 사용
	@Query(value = "select * from "
			+ "(select rownum rn, tt.* from "
			+ "(select * from board order by seq desc) tt) "
			+ "where rn >= :startNum and rn <= :endNum", nativeQuery = true)
	List<Board> findByStartnumAndEndnum(@Param("startNum") int startNum,
									 @Param("endNum") int endNum);
}
