package com.iktpreobuka.elektronskiDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.NastavnikEntity;

public interface NastavnikRepository extends CrudRepository<NastavnikEntity, Integer>{

	public NastavnikEntity findByUsername(String username);

}
