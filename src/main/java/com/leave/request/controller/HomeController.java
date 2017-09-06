/**
 * 
 */
package com.leave.request.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.leave.request.model.Notification;
import com.leave.request.model.LeaveRequest;
import com.leave.request.model.UserModel;
import com.leave.request.service.NotificationService;
import com.leave.request.service.MyTaskService;
import com.leave.request.service.RequestService;
import com.leave.request.service.UserService;
import com.leave.request.util.SecurityUtil;
import com.leave.request.validator.UserValidator;
import com.mysql.jdbc.Security;

/**
 * @author Eraine
 *
 */
@Controller
public class HomeController {

	private final static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private MyTaskService taskService;
	
	@Autowired
	private NotificationService alertService;
	
	@GetMapping(value = {"/", "/home"})
	public String home(@ModelAttribute("error") String error, @ModelAttribute("requestReviewed") String requestReviewed,
			Model model) {
		model.addAttribute("user", SecurityUtil.getUsername());
		model.addAttribute("error", error);
		model.addAttribute("requestReviewed", requestReviewed);
		
		Integer count = taskService.findAllTasksByUsername(SecurityUtil.getUsername()).size();
		model.addAttribute("count", count);
		
		List<Notification> notifications = alertService.findAllByUsername(SecurityUtil.getUsername());
		model.addAttribute("notifications", notifications);
		
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("error", "Your username or password is invalid.");
		}

		if (logout != null) {
			model.addAttribute("message", "You have been successfully logged-out.");
		}

		return "login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userForm", new UserModel());
		return "register";
	}

	@PostMapping("/register")
	public String processRegister(@ModelAttribute("userForm") UserModel userModel, BindingResult bindingResult,
			Model model) {
		userValidator.validate(userModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "register";
		}
		
		userService.register(userModel);

		return "register";
	}
	
	@GetMapping("/json/get-all-request")
	public ResponseEntity<List<LeaveRequest>> getAllTasks(Model model) {
		List<LeaveRequest> requests = requestService.findAllByUsername(SecurityUtil.getUsername());
		return new ResponseEntity<List<LeaveRequest>>(requests, HttpStatus.OK);
	}
}
