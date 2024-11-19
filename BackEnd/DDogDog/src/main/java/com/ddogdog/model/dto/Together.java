package com.ddogdog.model.dto;

public class Together {
    private Long togetherId;
    private Long tradeId;
    private Integer petId;

    // Getters and Setters
    public Long getTogetherId() {
        return togetherId;
    }

    public void setTogetherId(Long togetherId) {
        this.togetherId = togetherId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }
}
