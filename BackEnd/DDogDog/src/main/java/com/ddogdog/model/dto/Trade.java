package com.ddogdog.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class Trade {
    private Long tradeId;
    private String kind;
    private LocalDateTime tradeStartTime;
    private LocalDateTime tradeEndTime;
    private Long cost;
    private String region;
    private String detail;
    private int state;
    private boolean largeDog;
    private String superId;
    private String userId;
    private List<Integer> pets;
	public Long getTradeId() {
		return tradeId;
	}
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public LocalDateTime getTradeStartTime() {
		return tradeStartTime;
	}
	public void setTradeStartTime(LocalDateTime tradeStartTime) {
		this.tradeStartTime = tradeStartTime;
	}
	public LocalDateTime getTradeEndTime() {
		return tradeEndTime;
	}
	public void setTradeEndTime(LocalDateTime tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
	}
	public Long getCost() {
		return cost;
	}
	public void setCost(Long cost) {
		this.cost = cost;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public boolean isLargeDog() {
		return largeDog;
	}
	public void setLargeDog(boolean largeDog) {
		this.largeDog = largeDog;
	}
	public String getSuperId() {
		return superId;
	}
	public void setSuperId(String superId) {
		this.superId = superId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<Integer> getPets() {
		return pets;
	}
	public void setPets(List<Integer> pets) {
		this.pets = pets;
	}

}
