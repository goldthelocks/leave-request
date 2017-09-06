/**
 * 
 */
package com.leave.request.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Eraine
 *
 */
@Entity
@Table(name = "notification")
public class Notification extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String username;

	@Column
	private String message;

	@Column(name = "is_acknowledged")
	private Boolean isAcknowledged;

	public Notification() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the isAcknowledged
	 */
	public Boolean getIsAcknowledged() {
		return isAcknowledged;
	}

	/**
	 * @param isAcknowledged
	 *            the isAcknowledged to set
	 */
	public void setIsAcknowledged(Boolean isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}

}
