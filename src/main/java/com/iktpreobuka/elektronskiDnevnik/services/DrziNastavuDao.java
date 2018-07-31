package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskiDnevnik.entities.DrziNastavuEntity;

public interface DrziNastavuDao {
	
	//public List<DrziNastavuEntity> findDrziNastavuByPredajeOdeljnje(Integer predajeId, Integer odeljenjeId);

	List<DrziNastavuEntity> findDrziNastavuByOdeljenje(Integer id);

	List<DrziNastavuEntity> findDrziNastavuByPredaje(Integer id);

}
