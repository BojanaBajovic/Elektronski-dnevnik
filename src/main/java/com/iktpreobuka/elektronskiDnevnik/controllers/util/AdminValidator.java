package com.iktpreobuka.elektronskiDnevnik.controllers.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.elektronskiDnevnik.dto.AdminDTO;

@Component
public class AdminValidator implements Validator {
	@Override
	public boolean supports(Class<?> myClass) {
		return AdminDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdminDTO admin = (AdminDTO) target;
		if (!admin.getPassword().equals(admin.getRepeatedPassword())) {
			errors.reject("400", "Passwords must be the same");
		}
	}
	
}
