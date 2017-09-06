/**
 * 
 */
package com.leave.request.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leave.request.dto.RequestApprovalDto;
import com.leave.request.model.LeaveRequest;
import com.leave.request.repository.LeaveRequestRepository;
import com.leave.request.util.SecurityUtil;

/**
 * @author Eraine
 *
 */
@Service("requestService")
public class RequestServiceImpl implements RequestService {

	private final static Logger logger = LoggerFactory.getLogger(RequestTypeService.class);

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Override
	public List<LeaveRequest> findAllByUsername(String username) {
		return leaveRequestRepository.findAllByCreateBy(username);
	}

	@Override
	public void save(LeaveRequest leaveRequest) {
		leaveRequestRepository.save(leaveRequest);
	}

	@Override
	public void submit(LeaveRequest leaveRequest) {
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("processes/employee_leave_workflow.bpmn20.xml").deploy();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployment.getId()).singleResult();

		if (processDefinition == null) {
			logger.error("Workflow does not exist.");
			return;
		}

		// check if there exists a process for this leave request
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(String.valueOf(leaveRequest.getId())).singleResult();

		// if there is none, start one
		if (processInstance == null) {
			logger.info("Starting process instance...");
			processInstance = runtimeService.startProcessInstanceByKey(processDefinition.getKey(),
					String.valueOf(leaveRequest.getId()));
		}

		Map<String, Object> variables = new HashMap<>();
		variables.put("employeeUsername", leaveRequest.getCreateBy());
		variables.put("reviewer", ""); // fill this later
		variables.put("leaveId", leaveRequest.getId());
		variables.put("startDate", leaveRequest.getStartDate());
		variables.put("endDate", leaveRequest.getEndDate());

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.singleResult();
		taskService.setAssignee(task.getId(), SecurityUtil.getUsername());
		taskService.setVariables(task.getId(), variables);
		taskService.complete(task.getId());
	}

	@Override
	public void sendAlert(DelegateExecution execution) {
		// TODO Auto-generated method stub

	}

	@Override
	public LeaveRequest findById(Long id) {
		return leaveRequestRepository.findOne(id);
	}

	@Override
	public void approveOrReject(RequestApprovalDto requestApprovalDto) {
		logger.info("start: approveOrReject");
		Task task = taskService.createTaskQuery().taskId(requestApprovalDto.getTaskId()).includeProcessVariables()
				.singleResult();

		if (task == null) {
			logger.error("This task does not exist");
			return;
		}

		runtimeService.setVariable(task.getExecutionId(), "isApproved", requestApprovalDto.getIsApproved());
		taskService.setAssignee(task.getId(), SecurityUtil.getUsername());
		taskService.addComment(task.getId(), task.getProcessInstanceId(), requestApprovalDto.getComment() != null ? requestApprovalDto.getComment() : "");
		taskService.complete(task.getId());
		logger.info("end: approveOrReject");
	}

}
