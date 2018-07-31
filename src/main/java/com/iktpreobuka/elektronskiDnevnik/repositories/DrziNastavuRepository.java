package com.iktpreobuka.elektronskiDnevnik.repositories;

import javax.validation.Valid;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.dto.DrziNastavuDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.DrziNastavuEntity;

public interface DrziNastavuRepository extends CrudRepository<DrziNastavuEntity, Integer>{

	}
