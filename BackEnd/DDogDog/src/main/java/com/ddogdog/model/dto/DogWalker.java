package com.ddogdog.model.dto;

public class DogWalker {
    private String userId;
    private Boolean weekdayAm;
    private Boolean weekdayPm;
    private Boolean weekendAm;
    private Boolean weekendPm;
    private Boolean all;
    private Integer time;
    private Boolean handle;
    private String introduce;

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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
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
