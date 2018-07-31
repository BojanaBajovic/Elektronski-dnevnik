package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.OdeljenjeEntity;

public interface OdeljenjeDao {

	public OdeljenjeEntity findOdeljenjeByName(String name);

}
