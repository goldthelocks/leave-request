/**
 * 
 */
package com.leave.request.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.leave.request.model.LeaveRequest;

/**
 * @author Eraine
 *
 */
@Component
public class RequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return LeaveRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		LeaveRequest leaveRequest = (LeaveRequest) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "requestType", "message.notEmpty.requestType", "Request type must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "message.notEmpty.startDate", "Start Date must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "message.notEmpty.endDate", "End Date must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reason", "message.notEmpty.reason", "Reason must not be empty.");
		
	}

}
