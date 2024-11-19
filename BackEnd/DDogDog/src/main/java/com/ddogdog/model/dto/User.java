package com.ddogdog.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
	private String userId;
	private String password;
	private String nickname;
	private LocalDateTime birth;
	private String phone;
	private boolean gender;
	private double height;
	private double weight;
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	private String region;
	private int exp;
	private boolean dogWalker;
	private int hireCnt;
	private int hiredCnt;
	private double bpm;
	public String getUserId() {
		return userId;
	}
	public LocalDateTime getBirth() {
		return birth;
	}
	public void setBirth(LocalDateTime birth) {
		this.birth = birth;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public boolean isDogWalker() {
		return dogWalker;
	}
	public void setDogWalker(boolean dogWalker) {
		this.dogWalker = dogWalker;
	}
	public int getHireCnt() {
		return hireCnt;
	}
	public void setHireCnt(int hireCnt) {
		this.hireCnt = hireCnt;
	}
	public int getHiredCnt() {
		return hiredCnt;
	}
	public void setHiredCnt(int hiredCnt) {
		this.hiredCnt = hiredCnt;
	}
	public double getBpm() {
		return bpm;
	}
	public void setBpm(double bpm) {
		this.bpm = bpm;
	}
}
