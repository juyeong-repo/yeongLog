package com.project.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.blog.dto.ReplySaveRequestDto;
import com.project.blog.model.Board;
import com.project.blog.model.User;
import com.project.blog.repository.BoardRepository;
import com.project.blog.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
//	public BoardService(BoardRepository bRepo, ReplyRepository rRepo) {
//		this.boardRepository = bRepo;
//		this.replyRepository = rRepo;
//	}

	@Transactional
	public void postWrite(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		board.setType(1); 
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> postList(Pageable pageable){
		return boardRepository.findAll(pageable); 
	}
	

	public Page<Board> postListSample (Pageable pageable) {
		return boardRepository.findAll(pageable);	
	}
	
	
	@Transactional(readOnly = true)
	public Board postDetail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void postDelete(int id) {
		System.out.println("글삭제하기 : "+id);
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void postUpdate(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹 - 자동 업데이트가 됨. db flush
	}
	
	@Transactional
	public void replyWrite(ReplySaveRequestDto replySaveRequestDto) {
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), 
				replySaveRequestDto.getAnswerNum(), replySaveRequestDto.getContent(), replySaveRequestDto.getParentNum(), replySaveRequestDto.getReplyGroup());
		System.out.println("BoardService : "+result);
	}
	
	@Transactional
	public void replyDelete(int replyId) {
		replyRepository.deleteById(replyId);
	}
}
