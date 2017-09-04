/**
 * 
 */
package com.leave.request.service;

import java.util.List;

import com.leave.request.model.RequestType;

/**
 * @author Eraine
 *
 */
public interface RequestTypeService {

	void save(RequestType requestType);
	
	List<RequestType> findAll();
	
	Long getCount();
}
