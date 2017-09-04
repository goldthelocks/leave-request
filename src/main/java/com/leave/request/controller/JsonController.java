/**
 * 
 */
package com.leave.request.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leave.request.model.RequestType;
import com.leave.request.model.Role;
import com.leave.request.service.RequestTypeService;
import com.leave.request.service.RoleService;

/**
 * @author Eraine
 *
 */
@RestController
public class JsonController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RequestTypeService requestTypeService;
	
	@GetMapping("/json/all/roles")
	public ResponseEntity<List<Role>> getAllRoles() {
		return new ResponseEntity<List<Role>>(roleService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/json/all/requesttypes")
	public ResponseEntity<List<RequestType>> getAllRequestTypes() {
		return new ResponseEntity<List<RequestType>>(requestTypeService.findAll(), HttpStatus.OK);
	}
}
