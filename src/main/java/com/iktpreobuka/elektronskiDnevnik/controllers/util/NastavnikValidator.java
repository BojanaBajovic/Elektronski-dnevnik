package com.iktpreobuka.elektronskiDnevnik.controllers.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.elektronskiDnevnik.dto.NastavnikDTO;

@Component
public class NastavnikValidator implements Validator {
	@Override
	public boolean supports(Class<?> myClass) {
		return NastavnikDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NastavnikDTO nastavnik = (NastavnikDTO) target;
		if (!nastavnik.getPassword().equals(nastavnik.getRepeatedPassword())) {
			errors.reject("400", "Passwords must be the same");
		}
	}
	
}
