package com.ddogdog.model.dto;

public class PetLog {
	private Long plogId;
	private String note;
	private int small;
	private int big;
	public Long getPlogId() {
		return plogId;
	}
	public void setPlogId(Long plogId) {
		this.plogId = plogId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getSmall() {
		return small;
	}
	public void setSmall(int small) {
		this.small = small;
	}
	public int getBig() {
		return big;
	}
	public void setBig(int big) {
		this.big = big;
	}
}
