/**
 * 
 */
package com.leave.request.service;

import java.util.List;

import com.leave.request.dto.MyHistoryTask;
import com.leave.request.dto.MyTask;

/**
 * @author Eraine
 *
 */
public interface MyTaskService {

	List<MyTask> findAllTasksByUsername(String username);
	
	MyTask findTaskByTaskId(String taskId);
	
	List<MyHistoryTask> getTaskHistory(String leaveId);
	
}
