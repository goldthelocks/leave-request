/**
 * 
 */
package com.leave.request.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricTaskInstance;
import org.flowable.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leave.request.dto.MyHistoryTask;
import com.leave.request.dto.MyTask;

/**
 * @author Eraine
 *
 */
@Service
@Transactional
public class MyTaskServiceImpl implements MyTaskService {

	private final static Logger logger = LoggerFactory.getLogger(MyTaskServiceImpl.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Override
	public List<MyTask> findAllTasksByUsername(String username) {
		List<MyTask> myTasks = new ArrayList<>();
		List<Task> tasks = taskService.createTaskQuery().includeProcessVariables().taskCandidateOrAssigned(username)
				.list();
		logger.debug("+++ found {} tasks for {}", tasks.size(), username);

		for (Task task : tasks) {
			MyTask myTask = buildMyTask(task);
			myTasks.add(myTask);
		}

		return myTasks;
	}

	@Override
	public MyTask findTaskByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().includeProcessVariables().taskId(taskId).singleResult();
		if (task == null) {
			logger.info("No task found for this id!");
			return new MyTask();
		}

		MyTask myTask = buildMyTask(task);
		return myTask;
	}

	private MyTask buildMyTask(Task task) {
		MyTask myTask = new MyTask();
		myTask.setId(task.getId());
		myTask.setName(task.getName());
		myTask.setDescription(task.getDescription());
		myTask.setOwner(task.getOwner());
		myTask.setAssignee(task.getAssignee());
		myTask.setTaskDefinitionKey(task.getTaskDefinitionKey());
		myTask.setProcessVariables(task.getProcessVariables());
		return myTask;
	}

	@Override
	public List<MyHistoryTask> getTaskHistory(String leaveId) {
		List<MyHistoryTask> historyTaskList = new ArrayList<>();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(leaveId).singleResult();

		if (historicProcessInstance == null) {
			return historyTaskList;
		}

		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
				.includeTaskLocalVariables().processInstanceBusinessKey(leaveId).list();
		
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
			MyHistoryTask myHistoryTask = buildMyHistoryTask(historicTaskInstance);
			historyTaskList.add(myHistoryTask);
		}
		
		sort(historyTaskList);
		return historyTaskList;
	}
	
	private MyHistoryTask buildMyHistoryTask(HistoricTaskInstance historicTaskInstance) {
		MyHistoryTask myHistoryTask = new MyHistoryTask();
		myHistoryTask.setId(historicTaskInstance.getId());
		myHistoryTask.setName(historicTaskInstance.getName());
		myHistoryTask.setCommentList(taskService.getTaskComments(historicTaskInstance.getId()));
		Map<String, Object> variables = historicTaskInstance.getTaskLocalVariables();
		myHistoryTask.setVariables(variables);
		myHistoryTask.setDateCompleted(historicTaskInstance.getEndTime());
		myHistoryTask.setAssignee(historicTaskInstance.getAssignee());
		return myHistoryTask;
	}
	
	private void sort(List<MyHistoryTask> historyTasks) {
		
		Collections.sort(historyTasks, new Comparator<MyHistoryTask>() {

			@Override
			public int compare(MyHistoryTask o1, MyHistoryTask o2) {
			    if (o1.getDateCompleted() == null) {
			        return (o2.getDateCompleted() == null) ? 0 : -1;
			    }
			    if (o2.getDateCompleted() == null) {
			        return 1;
			    }
			    return o2.getDateCompleted().compareTo(o1.getDateCompleted());
			}
			
		});
	}

}
