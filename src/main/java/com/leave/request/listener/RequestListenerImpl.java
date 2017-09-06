/**
 * 
 */
package com.leave.request.listener;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.DelegateTask;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leave.request.constants.RequestStatusEnum;
import com.leave.request.model.LeaveRequest;
import com.leave.request.repository.LeaveRequestRepository;
import com.leave.request.util.SecurityUtil;

/**
 * @author eotayde
 *
 */
@Service("requestListener")
public class RequestListenerImpl implements RequestListener {

	private final static Logger logger = LoggerFactory.getLogger(RequestListenerImpl.class);

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RuntimeService runtimeService;

	@Override
	public void onCreateTeamLeadReview(Execution execution, DelegateTask task) {
		logger.info("start: onCreateTeamLeadReview");
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(execution.getProcessInstanceId()).singleResult();
		String strLeaveId = processInstance.getBusinessKey();

		Map<String, Object> variables = task.getVariables();
		String reviewer = (String) variables.get("reviewer");
		logger.debug("reviewer: {} ", reviewer);

		// if reviewer exists, set the assignee
		if (StringUtils.isNotBlank(reviewer)) {
			logger.debug("reviewer exists!");
			User user = identityService.createUserQuery().userId(reviewer).singleResult();
			taskService.claim(task.getId(), user.getId());
		}

		// update the status
		logger.debug("update status...");
		LeaveRequest leaveRequest = leaveRequestRepository.findOne(Long.valueOf(strLeaveId));
		leaveRequest.setStatus(RequestStatusEnum.TEAM_LEAD_REVIEW.getValue());
		leaveRequest.setReviewedBy(SecurityUtil.getUsername());
		leaveRequestRepository.save(leaveRequest);

		logger.info("end: onCreateTeamLeadReview");
	}

	@Override
	public void onCompleteTeamLeadReview(DelegateTask task) {
		logger.info("start: onCompleteTeamLeadReview");
		// assign the reviewer, we need this so we'll be able to retrieve who
		// reviewed it
		Map<String, Object> variables = task.getVariables();
		String reviewer = (String) variables.get("reviewer");
		logger.debug("reviewer: {}", reviewer);

		if (StringUtils.isBlank(reviewer)) {
			logger.debug("reviewer is blank, assign to current user");
			variables.put("reviewer", SecurityUtil.getUsername());

			taskService.setVariables(task.getId(), variables);
		}

		logger.info("end: onCompleteTeamLeadReview");
	}

	@Override
	public void onCreateManagerReview(Execution execution, DelegateTask task) {
		logger.info("start: onCreateManagerReview");
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(execution.getProcessInstanceId()).singleResult();
		
		String strLeaveId = processInstance.getBusinessKey();

		// update the status
		logger.debug("update status...");
		LeaveRequest leaveRequest = leaveRequestRepository.findOne(Long.valueOf(strLeaveId));
		leaveRequest.setStatus(RequestStatusEnum.MANAGER_REVIEW.getValue());
		leaveRequest.setReviewedBy(SecurityUtil.getUsername());
		leaveRequestRepository.save(leaveRequest);

		logger.info("end: onCreateManagerReview");
	}

	@Override
	public void onCompleteManagerReview(DelegateTask task) {
	}

	@Override
	public void onApprove(DelegateExecution execution) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(execution.getProcessInstanceId()).singleResult();

		String strLeaveId = processInstance.getBusinessKey();

		LeaveRequest leaveRequest = leaveRequestRepository.findOne(Long.valueOf(strLeaveId));
		leaveRequest.setStatus(RequestStatusEnum.APPROVED.getValue());		
		leaveRequest.setApprovedBy(SecurityUtil.getUsername());
		leaveRequestRepository.save(leaveRequest);
	}

	@Override
	public void onReject(DelegateExecution execution) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(execution.getProcessInstanceId()).singleResult();

		String strLeaveId = processInstance.getBusinessKey();

		LeaveRequest leaveRequest = leaveRequestRepository.findOne(Long.valueOf(strLeaveId));
		leaveRequest.setStatus(RequestStatusEnum.REJECTED.getValue());
		leaveRequestRepository.save(leaveRequest);
	}

}
