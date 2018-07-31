package com.iktpreobuka.elektronskiDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.PredajeEntity;

public interface PredajeRepository extends CrudRepository<PredajeEntity, Integer> {
}
