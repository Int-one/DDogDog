package com.ddogdog.model.dto;

public class PetLog {
    private Long plogId;
    private String notes;
    private Integer small;
    private Integer big;
    private Long logId;
    private Integer petId;

    // Getters and Setters
    public Long getPlogId() {
        return plogId;
    }

    public void setPlogId(Long plogId) {
        this.plogId = plogId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getSmall() {
        return small;
    }

    public void setSmall(Integer small) {
        this.small = small;
    }

    public Integer getBig() {
        return big;
    }

    public void setBig(Integer big) {
        this.big = big;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }
}
