package com.project.blog.service;

import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.project.blog.model.Board;
import com.project.blog.model.User;
import com.project.blog.repository.BoardRepository;
import com.project.blog.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	
	@Transactional
	public void noticeWrite (Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		noticeRepository.save(board);	
	}
	
	@Transactional(readOnly = true)
	public Page<Board> noticeList(Pageable pageable) {
		return noticeRepository.findAll(pageable);
		
	}
	
	//관리자는 사용자 id에 등록할 때 넘버를 다르게 한다든가.. db에서 구분이 필요하다. -> 현재 role로 되어있음
	//boardService와 소스가 거의 유사해서 하나로 통합하는 리팩토링이 필요. -> role타입만 다르게하면 해결될 문제 
	@Transactional(readOnly = true)
	public Board noticeDetail (int id) { 
		return noticeRepository.findById(id)
				.orElseThrow (()-> {
					return new IllegalArgumentException("공지사항 상세보기 실패: 작성 관리자 정보를 찾을 수 없습니다.");
				});	
	}
	
	@Transactional
	public void postDelete(int id) {
		noticeRepository.deleteById(id);
	}
	
	@Transactional
	public void noticeUpdate(int id, Board requestNotice) {
		Board board = noticeRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("공지사항 찾기 실패: 작성 관리자 정보를 찾을 수 없습니다.");
				});
	}
	
	
	//공지사항은 댓글 기능없음

}
