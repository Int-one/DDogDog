package com.ddogdog.model.dto;

import java.time.LocalDateTime;

public class Pet {
    private Integer petId;
    private String petName;
    private LocalDateTime petBirth;
    private Boolean petGender;
    private Boolean castration;
    private String group;
    private String breed;
    private double petWeight;
    private String petPhoto;
    private String ps;
    private String userId;
    private LocalDateTime endTime;
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public LocalDateTime getPetBirth() {
		return petBirth;
	}
	public void setPetBirth(LocalDateTime petBirth) {
		this.petBirth = petBirth;
	}
	public Boolean getPetGender() {
		return petGender;
	}
	public void setPetGender(Boolean petGender) {
		this.petGender = petGender;
	}
	public Boolean getCastration() {
		return castration;
	}
	public void setCastration(Boolean castration) {
		this.castration = castration;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public double getPetWeight() {
		return petWeight;
	}
	public void setPetWeight(double petWeight) {
		this.petWeight = petWeight;
	}
	public String getPetPhoto() {
		return petPhoto;
	}
	public void setPetPhoto(String petPhoto) {
		this.petPhoto = petPhoto;
	}
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	    
}