package com.iktpreobuka.elektronskiDnevnik.controllers.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.elektronskiDnevnik.dto.UcenikDTO;

@Component
public class UcenikValidator implements Validator {
	@Override
	public boolean supports(Class<?> myClass) {
		return UcenikDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UcenikDTO ucenik = (UcenikDTO) target;
		if (!ucenik.getPassword().equals(ucenik.getRepeatedPassword())) {
			errors.reject("400", "Passwords must be the same");
		}
	}
	
}
