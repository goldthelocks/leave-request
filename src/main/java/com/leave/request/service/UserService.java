/**
 * 
 */
package com.leave.request.service;

import com.leave.request.model.UserModel;

/**
 * @author Eraine
 *
 */
public interface UserService {

	void register(UserModel userModel);
	
	boolean isUserExisting(UserModel userModel);
	
}
