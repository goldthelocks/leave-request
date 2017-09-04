/**
 * 
 */
package com.leave.request.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.flowable.engine.IdentityService;
import org.flowable.idm.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.leave.request.exception.UsernameExistsException;
import com.leave.request.model.Role;
import com.leave.request.model.UserModel;
import com.leave.request.repository.RoleRepository;
import com.leave.request.repository.UserRepository;

/**
 * @author Eraine
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private IdentityService identityService;
	
	@Override
	public void register(UserModel userModel) throws UsernameExistsException {
		logger.debug("+++ role: +++ " + userModel.getRoles().toString());
		userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
//		Role userRole = roleRepository.findByName("EMPLOYEE");
//		userModel.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
		// create membership for flowable
		createMembership(userModel.getUsername(), userModel.getRoles());
		
		userRepository.save(userModel);
	}

	/**
	 * @param username
	 * @param roles
	 */
	private void createMembership(String username, Set<Role> roles) {
		User user = identityService.newUser(username);
		user.setId(username);
		identityService.saveUser(user);
		
		for (Role role : roles) {
			identityService.createMembership(username, role.getName().toLowerCase());
		}
	}

	@Override
	public boolean isUserExisting(UserModel userModel) {
		if (isUserExisting(userModel.getUsername())) {
			return true;
		}
		return false;
	}
	
	private boolean isUserExisting(String username) {
		UserModel user = userRepository.findByUsername(username);
		if (user != null) {
			return true;
		}
		
		return false;
	}

}
