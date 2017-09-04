/**
 * 
 */
package com.leave.request.dto;

/**
 * @author eotayde
 *
 */
public class RequestApprovalDto {

	public String taskId;
	public String leaveId;
	public String comment;
	public Boolean isApproved;

	public RequestApprovalDto() {
	}

	/**
	 * @param taskId
	 */
	public RequestApprovalDto(String taskId) {
		super();
		this.taskId = taskId;
	}
	
	/**
	 * @param taskId
	 * @param leaveId
	 */
	public RequestApprovalDto(String taskId, String leaveId) {
		super();
		this.taskId = taskId;
		this.leaveId = leaveId;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the isApproved
	 */
	public Boolean getIsApproved() {
		return isApproved;
	}

	/**
	 * @param isApproved
	 *            the isApproved to set
	 */
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * @return the leaveId
	 */
	public String getLeaveId() {
		return leaveId;
	}

	/**
	 * @param leaveId the leaveId to set
	 */
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestApprovalDto [taskId=" + taskId + ", leaveId=" + leaveId + ", comment=" + comment
				+ ", isApproved=" + isApproved + "]";
	}

	

}
