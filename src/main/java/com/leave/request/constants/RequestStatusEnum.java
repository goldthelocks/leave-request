/**
 * 
 */
package com.leave.request.constants;

/**
 * @author Eraine
 *
 */
public enum RequestStatusEnum {

	TEAM_LEAD_REVIEW("Team Lead Review"), 
	MANAGER_REVIEW("Manager Review"), 
	APPROVED("Approved"), 
	REJECTED("Rejected");

	private String value;

	/**
	 * @param value
	 */
	private RequestStatusEnum(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
