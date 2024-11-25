package com.ddogdog.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 DTO")
public class Board {
	private int boardId;
	private String title;
	private String userId;
	private String writer;
	private String content;
	private String regDate;
	private int viewCnt;

	public Board() {
	}

	public Board(String title, String userId, String content) {
		super();
		this.title = title;
		this.userId = userId;
		this.content = content;
	}

	public Board(int boardId, String title, String userId, String content, String regDate, int viewCnt) {
		this.boardId = boardId;
		this.title = title;
		this.userId = userId;
		this.content = content;
		this.regDate = regDate;
		this.viewCnt = viewCnt;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int id) {
		this.boardId = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getWriter() {
		return writer;
	}
	
	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", title=" + title + ", userId=" + userId + ", content=" + content
				+ ", regDate=" + regDate + ", viewCnt=" + viewCnt + "]";
	}
}
