package com.project.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.model.Board;

public interface NoticeRepository extends JpaRepository<Board, Integer> {	
	// entity class, id 값
	

}
