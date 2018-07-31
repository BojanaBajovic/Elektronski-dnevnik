package com.iktpreobuka.elektronskiDnevnik.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;


public interface UcenikRepository extends CrudRepository<UcenikEntity, Integer> {

	public List<UcenikEntity> findUcenikByodeljenje(Integer id) ;

	public Iterable<UcenikEntity> findAllById(Integer id);

	public List<UcenikEntity> findByImeStartingWith(char letter);

	public List<UcenikEntity> findAllByOrderByPrezimeAsc();

	public boolean existsById(UcenikEntity ucenikId);

	public UcenikEntity findByUsername(String username);

	

	
	//UcenikEntity findOne(Integer ucenik);

}
