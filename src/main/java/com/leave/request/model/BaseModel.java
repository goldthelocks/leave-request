/**
 * 
 */
package com.leave.request.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.leave.request.util.SecurityUtil;

/**
 * @author Eraine
 *
 */
@MappedSuperclass
public class BaseModel {

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "update_by")
	private String updateBy;

	public BaseModel() {
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param updateBy
	 *            the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@PrePersist
	public void prePersist() {
		createDate = new Date();
		updateDate = new Date();
		createBy = SecurityUtil.getUsername();
		updateBy = SecurityUtil.getUsername();
	}

	@PreUpdate
	public void preUpdate() {
		if (updateDate == null) {
			updateDate = new Date();
		}
		
		if (updateBy == null) {
			updateBy = SecurityUtil.getUsername();
		}
	}
}
