/**
 * 
 */
package com.leave.request.constants;

/**
 * @author Eraine
 *
 */
public enum UserRoleEnum {

	ADMIN("ADMIN"),
	HR("HR"),
	EMPLOYEE("EMPLOYEE"),
	TEAM_LEAD("TEAM_LEAD"),
	MANAGER("MANAGER");
	
	private String value;

	/**
	 * @param value
	 */
	private UserRoleEnum(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
