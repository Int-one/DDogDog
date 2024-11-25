package com.ddogdog.model.dao;

import java.util.List;

import com.ddogdog.model.dto.Pet;

public interface PetDao {
	public int insertPet(Pet user);
	
	public List<Pet> getPetByUser(String userId);
	
	public List<Pet> findPetByLogId(Long logId);
	
	public Pet selectOne(int petId);
	
	public int deletePet(int petId);
	
	public int updatePet(Pet user);
}
