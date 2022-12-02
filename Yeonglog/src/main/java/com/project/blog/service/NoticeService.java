package com.project.blog.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.blog.model.Board;
import com.project.blog.model.User;
import com.project.blog.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	//admin Servic
	private final NoticeRepository noticeRepository;
	
	
	@Transactional
	public void noticeWrite (Board board, User user) {
		board.setCount(0);
		board.setUser(user);	
		board.setType(0); //공지사항 = 1, 일반 포스트 =2 
		//여기서 if 분기로 리팩토링 가능 (composite boardService)	
		//나중말고 지금하자~
		noticeRepository.save(board);
	}
	
	
	@Transactional(readOnly = true)
	public Page<Board> noticeList(Pageable pageable) {	
		return noticeRepository.findAll(pageable);
	}
		

	
	
	
}
