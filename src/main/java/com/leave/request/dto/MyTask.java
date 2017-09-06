/**
 * 
 */
package com.leave.request.dto;

import java.util.Date;
import java.util.Map;

/**
 * @author Eraine
 *
 */
public class MyTask {

	private String id;
	private String name;
	private String description;
	private String owner;
	private String assignee;
	private String taskDefinitionKey;
	private Map<String, Object> processVariables;
	private Date createTime;

	public MyTask() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee
	 *            the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the taskDefinitionKey
	 */
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	/**
	 * @param taskDefinitionKey
	 *            the taskDefinitionKey to set
	 */
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	/**
	 * @return the processVariables
	 */
	public Map<String, Object> getProcessVariables() {
		return processVariables;
	}

	/**
	 * @param processVariables
	 *            the processVariables to set
	 */
	public void setProcessVariables(Map<String, Object> processVariables) {
		this.processVariables = processVariables;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
