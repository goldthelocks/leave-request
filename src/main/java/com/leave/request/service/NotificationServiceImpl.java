/**
 * 
 */
package com.leave.request.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leave.request.model.Notification;
import com.leave.request.repository.NotificationRepository;

/**
 * @author eotayde
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Override
	public void saveNotification(String username, String message) {
		// TODO
	}

	@Override
	public void sendAcknowledgement(Long id, String username) {		
	}

	@Override
	public List<Notification> findAllByUsername(String username) {
		return notificationRepository.findAllByUsername(username);
	}

}
