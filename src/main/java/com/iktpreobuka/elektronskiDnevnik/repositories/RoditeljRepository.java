package com.iktpreobuka.elektronskiDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.RoditeljEntity;

public interface RoditeljRepository extends CrudRepository<RoditeljEntity, Integer> {

	public RoditeljEntity findByUsername(String username);
	//RoditeljEntity findOne(Integer id); 
	 
	}
