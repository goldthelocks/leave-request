/**
 * 
 */
package com.leave.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leave.request.model.UserModel;

/**
 * @author Eraine
 *
 */
public interface UserRepository extends JpaRepository<UserModel, Long>{

	UserModel findByUsername(String username);
}
