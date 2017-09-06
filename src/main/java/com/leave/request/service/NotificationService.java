/**
 * 
 */
package com.leave.request.service;

import java.util.List;

import com.leave.request.model.Notification;

/**
 * @author eotayde
 *
 */
public interface NotificationService {

	List<Notification> findAllByUsername(String username);
	
	void saveNotification(String username, String message);
	
	void sendAcknowledgement(Long id, String username);
}
