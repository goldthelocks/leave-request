/**
 * 
 */
package com.leave.request.service;

import java.util.List;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.Execution;

import com.leave.request.model.LeaveRequest;

/**
 * @author Eraine
 *
 */
public interface RequestService {

	LeaveRequest findById(Long id);
	
	List<LeaveRequest> findAllByUsername(String username);
	
	void save(LeaveRequest leaveRequest);
	
	void submit(LeaveRequest leaveRequest);
	
	void teamLeadReview(Execution execution);
	
	void managerReview(Execution execution);
	
	void sendAlert(DelegateExecution execution);
	
}
