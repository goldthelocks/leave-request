/**
 * 
 */
package com.leave.request.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.flowable.engine.TaskService;
import org.flowable.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
