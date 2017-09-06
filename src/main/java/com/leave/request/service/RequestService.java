/**
 * 
 */
package com.leave.request.service;

import java.util.List;

import org.flowable.engine.delegate.DelegateExecution;

import com.leave.request.dto.RequestApprovalDto;
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

	void sendAlert(DelegateExecution execution);
	
	void approveOrReject(RequestApprovalDto requestApprovalDto);
	
}
