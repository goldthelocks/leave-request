/**
 * 
 */
package com.leave.request.service;

import org.flowable.engine.delegate.DelegateTask;
import org.flowable.engine.runtime.Execution;

/**
 * @author Eraine
 *
 */
public interface WorkflowService {

	void doTeamLeadReview(Execution execution, DelegateTask delegateTask);
	
	void doManagerReview(Execution execution, DelegateTask delegateTask);
}
