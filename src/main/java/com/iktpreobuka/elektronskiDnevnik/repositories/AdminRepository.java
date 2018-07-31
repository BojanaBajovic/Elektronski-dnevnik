package com.iktpreobuka.elektronskiDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.AdminEntity;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {

	public AdminEntity findByUsername(String username);

}
