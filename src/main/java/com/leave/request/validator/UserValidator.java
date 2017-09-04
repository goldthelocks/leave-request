/**
 * 
 */
package com.leave.request.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.leave.request.model.UserModel;
import com.leave.request.service.UserService;

/**
 * @author Eraine
 *
 */
@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserModel.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UserModel userModel = (UserModel) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "message.username", "Username is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.password", "Password is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roles", "message.roles", "Select at least one user role.");
		
		if (userService.isUserExisting(userModel)) {
			errors.rejectValue("username", "message.exists", "User already exists!");
		}
	}

}
