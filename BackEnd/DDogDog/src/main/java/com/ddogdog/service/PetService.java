package com.ddogdog.service;

import java.util.List;

import com.ddogdog.model.dto.Pet;

public interface PetService {

	public boolean createPet(Pet pet);
	
	public List<Pet> getPetByUser(String userId);
	
	public Pet getPetById(int petId);
	
	public boolean deletePet(int petId);
	
	public boolean updatePet(Pet pet);
}
