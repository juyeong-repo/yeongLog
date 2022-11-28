package com.project.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.project.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	//@Modifying
	//@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	//int mSave(int userId, int boardId, String content); // 업데이트된 행의 개수를 리턴해줌.  
	
	
	
	//인서트 할 때 +1의 구조로 넣어줘야 한다.
	@Modifying
	@Query(value = "INSERT INTO reply (userId, boardId, answerNum, content, createDate, parentNum, replyGroup) VALUES (?1, ?2, ?3, ?4, now(), ?5, ?6)", nativeQuery = true )
	int mSave (int userId, int boardId, int answerNum,
			String content, int parentNum, int replyGroup);
	
	
}