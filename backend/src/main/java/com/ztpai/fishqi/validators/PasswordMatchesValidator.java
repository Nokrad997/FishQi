package com.ztpai.fishqi.validators;

import com.ztpai.fishqi.DTO.RegistrationDTO;
import com.ztpai.fishqi.validators.interfaces.PasswordMatches;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
		implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		RegistrationDTO user = (RegistrationDTO) obj;
		
		if (user.getPassword() == null || user.getMatchingPassword() == null) {
            return false;
        }

		return user.getPassword().equals(user.getMatchingPassword());
	}
}