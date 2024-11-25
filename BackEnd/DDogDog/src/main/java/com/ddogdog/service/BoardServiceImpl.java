package com.ddogdog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddogdog.model.dao.BoardDao;
import com.ddogdog.model.dto.Board;
import com.ddogdog.model.dto.SearchCondition;

@Service
public class BoardServiceImpl implements BoardService{
	
//	@Autowired
//	private BoardDao boardDao;   // final을 사용하면 안된다
	
	private final BoardDao boardDao;
	
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	@Override
	public List<Board> getBoardList() {
		System.out.println("모든 게시글");
		return boardDao.selectAll();
	}

	@Override
	public Board readBoard(int id) {
		System.out.println("게시글 조회");
		boardDao.updateViewCnt(id);
		return boardDao.selectOne(id);
	}

	@Transactional
	@Override
	public void writeBoard(Board board) {
		System.out.println("게시글 작성");
		boardDao.insertBoard(board);
	}

	@Transactional
	@Override
	public boolean removeBoard(int id) {
		System.out.println("게시글 삭제");
		int result = boardDao.deleteBoard(id);
		System.out.println(result);
		
//		if(result == 1) return true;
//		return false;
		
		return result == 1;
	}

	@Transactional
	@Override
	public void modifyBoard(Board board) {
		System.out.println("게시글 수정");
		
//		Board tmp = boardDao.selectOne(board.getId());
//		tmp.setTitle(board.getTitle());
		boardDao.updateBoard(board);
	}

	@Override
	public List<Board> search(SearchCondition condition) {
		System.out.println("게시글 검색");
		return boardDao.search(condition);
	}

}
