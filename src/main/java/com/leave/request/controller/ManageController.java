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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leave.request.constants.UserRoleEnum;
import com.leave.request.dto.MyTask;
import com.leave.request.dto.RequestApprovalDto;
import com.leave.request.service.MyTaskService;
import com.leave.request.service.RequestService;
import com.leave.request.util.SecurityUtil;
import com.mysql.jdbc.Security;

/**
 * @author eotayde
 *
 */
@Controller
public class ManageController {

	private final static Logger logger = LoggerFactory.getLogger(ManageController.class);
	
	@Autowired
	private MyTaskService myTaskService;
	
	@GetMapping("/manage")
	public String manageEmployeeLeaves(RedirectAttributes redirectAttributes, Model model) {
		if (SecurityUtil.hasRole(UserRoleEnum.EMPLOYEE.getValue())) {		
			redirectAttributes.addFlashAttribute("error", "You are not authorized to view that page!");
			return "redirect:/home";
		}
		
		return "manage";
	}
	
	@GetMapping("/json/get-all-tasks")
	public ResponseEntity<List<MyTask>> getAllTasks(Model model) {
		List<MyTask> myTasks = myTaskService.findAllTasksByUsername(SecurityUtil.getUsername());
		return new ResponseEntity<List<MyTask>>(myTasks, HttpStatus.OK);
	}
	
}
