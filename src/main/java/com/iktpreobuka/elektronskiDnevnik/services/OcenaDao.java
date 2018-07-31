package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskiDnevnik.entities.OcenaEntity;

public interface OcenaDao {

	public List<OcenaEntity> findOcenaByUcenik(Integer id);

}
