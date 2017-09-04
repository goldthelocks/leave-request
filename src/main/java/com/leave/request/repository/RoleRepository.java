/**
 * 
 */
package com.leave.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leave.request.model.Role;

/**
 * @author Eraine
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
}
