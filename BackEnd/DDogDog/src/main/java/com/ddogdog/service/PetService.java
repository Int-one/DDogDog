package com.ddogdog.service;

import java.util.List;

import com.ddogdog.model.dto.Pet;

public interface PetService {

	public boolean create(Pet pet);
	
	public List<Pet> getAllPet();
	
	public Pet getPetById(int petId);
	
	public boolean removePet(int petId);
	
	public boolean modifyPet(Pet pet);
}
