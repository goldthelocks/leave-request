/**
 * 
 */
package com.leave.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leave.request.model.LeaveRequest;

/**
 * @author Eraine
 *
 */
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

	List<LeaveRequest> findAllByCreateBy(String createBy);
}
