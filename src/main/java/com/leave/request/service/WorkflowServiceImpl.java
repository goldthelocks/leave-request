/**
 * 
 */
package com.leave.request.service;

import org.flowable.engine.delegate.DelegateTask;
import org.flowable.engine.runtime.Execution;
import org.springframework.stereotype.Service;

/**
 * @author Eraine
 *
 */
@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService{

	/* (non-Javadoc)
	 * @see com.leave.request.service.WorkflowService#doTeamLeadReview(org.flowable.engine.runtime.Execution, org.flowable.engine.delegate.DelegateTask)
	 */
	@Override
	public void doTeamLeadReview(Execution execution, DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.leave.request.service.WorkflowService#doManagerReview(org.flowable.engine.runtime.Execution, org.flowable.engine.delegate.DelegateTask)
	 */
	@Override
	public void doManagerReview(Execution execution, DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		
	}

	
}
