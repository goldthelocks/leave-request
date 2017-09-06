/**
 * 
 */
package com.leave.request.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.DelegateTask;
import org.flowable.engine.runtime.Execution;

/**
 * @author eotayde
 *
 */
public interface RequestListener {

	void onCreateTeamLeadReview(Execution execution, DelegateTask task);
	
	void onCompleteTeamLeadReview(DelegateTask task);
	
	void onCreateManagerReview(Execution execution, DelegateTask task);
	
	void onCompleteManagerReview(DelegateTask task);
	
	void onApprove(DelegateExecution execution);
	
	void onReject(DelegateExecution execution);
	
}
