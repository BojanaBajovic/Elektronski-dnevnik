package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;

public interface UcenikDao {
	 public List<UcenikEntity> findUcenikByodeljenje(Integer id) ;
	 
	 
	 public List<UcenikEntity> findUcenikByodeljenjeSort(Integer id) ;


     public List<UcenikEntity> findUcenikByroditelj(Integer id) ;

	List<UcenikEntity> findUcenikByodeljenjeIRazred(Integer id, Integer brojRazreda);


	public List<UcenikEntity> findAllByOrderByPrezimeAsc(Integer id);

}

