package com.ddogdog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ddogdog.model.dao.PetDao;
import com.ddogdog.model.dto.Pet;

@Service
public class PetServiceImpl implements PetService {
	
	private PetDao petdao;
	
	PetServiceImpl(PetDao petdao){
		this.petdao = petdao;
	}

	@Override
	public boolean createPet(Pet pet) {
		int result = petdao.insertPet(pet); 
		return result != 0;
	}

	@Override
	public List<Pet> getPetByUser(String userId) {
		List<Pet> list = petdao.getPetByUser(userId);
		return list;
	}
	
	@Override
	public List<Pet> findPetByLogId(Long logId) {
		List<Pet> list = petdao.findPetByLogId(logId);
		return list;
	}

	@Override
	public Pet getPetById(int petId) {
		Pet pet = petdao.selectOne(petId);
		return pet;
	}

	@Override
	public boolean deletePet(int petId) {
		int result = petdao.deletePet(petId);
		return result != 0;
	}

	@Override
	public boolean updatePet(Pet pet) {
		int result = petdao.updatePet(pet);
		return result != 0;
	}

}
