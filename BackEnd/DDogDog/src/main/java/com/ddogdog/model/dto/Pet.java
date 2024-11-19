package com.ddogdog.model.dto;

public class Pet {
    private Integer petId;
    private String name;
    private String birth;
    private Boolean gender;
    private Boolean castration;
    private String group;
    private String breed = "";
    private double weight = 0;
    private String userId;
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
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
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Pet [petId=" + petId + ", name=" + name + ", birth=" + birth + ", gender=" + gender + ", castration="
				+ castration + ", group=" + group + ", userId=" + userId + "]";
	}
}