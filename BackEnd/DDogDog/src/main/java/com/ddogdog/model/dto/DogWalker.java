package com.ddogdog.model.dto;

import java.time.LocalDateTime;

public class DogWalker {
    private String userId;
    private Boolean weekdayAm = false;
    private Boolean weekdayPm = false;
    private Boolean weekendAm = false;
    private Boolean weekendPm = false;
    private Boolean all = false;
    private Integer ableTime = 0;
    private Boolean handle = false;
    private String introduce = "";
    private String nickname;
	private LocalDateTime birth;
	private String phone;
	private boolean gender;
	private double height;
	private double weight;
	private String region;
	private int exp;
	private boolean dogWalker;
	private int hireCnt;
	private int hiredCnt;
	private double bpm;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public LocalDateTime getBirth() {
		return birth;
	}

	public void setBirth(LocalDateTime birth) {
		this.birth = birth;
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
    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getWeekdayAm() {
        return weekdayAm;
    }

    public void setWeekdayAm(Boolean weekdayAm) {
        this.weekdayAm = weekdayAm;
    }

    public Boolean getWeekdayPm() {
        return weekdayPm;
    }

    public void setWeekdayPm(Boolean weekdayPm) {
        this.weekdayPm = weekdayPm;
    }

    public Boolean getWeekendAm() {
        return weekendAm;
    }

    public void setWeekendAm(Boolean weekendAm) {
        this.weekendAm = weekendAm;
    }

    public Boolean getWeekendPm() {
        return weekendPm;
    }

    public void setWeekendPm(Boolean weekendPm) {
        this.weekendPm = weekendPm;
    }

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public Integer getAbleTime() {
        return ableTime;
    }

    public void setAbleTime(Integer time) {
        this.ableTime = time;
    }

    public Boolean getHandle() {
        return handle;
    }

    public void setHandle(Boolean handle) {
        this.handle = handle;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
