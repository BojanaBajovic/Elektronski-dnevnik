package com.iktpreobuka.elektronskiDnevnik.controllers.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.iktpreobuka.elektronskiDnevnik.dto.RoditeljDTO;

@Component
public class RoditeljValidator implements Validator {
	@Override
	public boolean supports(Class<?> myClass) {
		return RoditeljDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RoditeljDTO roditelj = (RoditeljDTO) target;
		if (!roditelj.getPassword().equals(roditelj.getRepeatedPassword())) {
			errors.reject("400", "Passwords must be the same");
		}
	}
	
}
