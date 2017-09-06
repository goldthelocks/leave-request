/**
 * 
 */
package com.leave.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leave.request.model.Notification;

/**
 * @author eotayde
 *
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findAllByUsername(String username);
}
