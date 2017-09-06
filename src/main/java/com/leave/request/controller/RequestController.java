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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leave.request.constants.RequestStatusEnum;
import com.leave.request.dto.MyHistoryTask;
import com.leave.request.dto.MyTask;
import com.leave.request.dto.RequestApprovalDto;
import com.leave.request.model.LeaveRequest;
import com.leave.request.model.RequestType;
import com.leave.request.service.MyTaskService;
import com.leave.request.service.RequestService;
import com.leave.request.util.SecurityUtil;
import com.leave.request.validator.RequestValidator;

/**
 * @author Eraine
 *
 */
@Controller
@SessionAttributes({"requestForm"})
public class RequestController {

	private final static Logger logger = LoggerFactory.getLogger(RequestController.class);
	
	@Autowired
	private RequestValidator validator;

	@Autowired
	private RequestService requestService;

	@Autowired
	private MyTaskService myTaskService;

	@GetMapping("/request")
	public String request(Model model) {
		model.addAttribute("requestForm", new LeaveRequest());
		return "request";
	}

	@PostMapping("/request")
	public String processRequest(@ModelAttribute("requestForm") LeaveRequest leaveRequest, BindingResult bindingResult,
			Model model) {
		validator.validate(leaveRequest, bindingResult);

		if (bindingResult.hasErrors()) {
			return "request";
		}

		requestService.save(leaveRequest);
		requestService.submit(leaveRequest);

		model.addAttribute("success", true);

		return "request";
	}

	@GetMapping("/view/{id}")
	public String requestView(@PathVariable("id") Long id, Model model) {
		LeaveRequest leaveRequest = requestService.findById(id);

		model.addAttribute("leaveRequest", leaveRequest);
		
		return "request-view";
	}
	
	@GetMapping("/edit/{id}")
	public String requestEdit(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		LeaveRequest leaveRequest = requestService.findById(id);
		
		if (!leaveRequest.getStatus().equals(RequestStatusEnum.REJECTED.getValue())) {
			redirectAttributes.addFlashAttribute("error", "You are not authorized to view that page!");
			return "redirect:/home";
		}
		
		model.addAttribute("requestForm", leaveRequest);
		return "request-edit";
	}
	
	@PostMapping("/edit")
	public String processRequestEdit(@ModelAttribute("requestForm") LeaveRequest leaveRequest, BindingResult bindingResult,
			Model model) {
		validator.validate(leaveRequest, bindingResult);

		if (bindingResult.hasErrors()) {
			return "request-edit";
		}

		requestService.save(leaveRequest);
		requestService.submit(leaveRequest);

		model.addAttribute("success", true);

		return "request-edit";
	}

	@GetMapping("/json/get-task-history/{id}")
	public ResponseEntity<List<MyHistoryTask>> getAllHistoryTask(@PathVariable("id") Long id, Model model) {
		List<MyHistoryTask> myHistoryTask = myTaskService.getTaskHistory(String.valueOf(id));
		return new ResponseEntity<List<MyHistoryTask>>(myHistoryTask, HttpStatus.OK);
	}

	@GetMapping("/review/{id}")
	public String manageEmployeeLeavesReview(@PathVariable("id") String taskId, RedirectAttributes redirectAttributes,
			Model model) {
		MyTask myTask = myTaskService.findTaskByTaskId(taskId);

		LeaveRequest leaveRequest = requestService.findById((Long) myTask.getProcessVariables().get("leaveId"));

		if (SecurityUtil.getUsername().equals(leaveRequest.getCreateBy())) {
			redirectAttributes.addFlashAttribute("error", "You are not authorized to view that page!");
			return "redirect:/home";
		}

		model.addAttribute("leaveRequest", leaveRequest);
		model.addAttribute("taskId", myTask.getId());
		RequestApprovalDto dto = new RequestApprovalDto(taskId, String.valueOf(leaveRequest.getId()));
		model.addAttribute("requestApprovalForm", dto);

		return "request-review";
	}

	@PostMapping(value = "/review/submit", params = "action=approve")
	public String processApproveRequest(@ModelAttribute("requestApprovalForm") RequestApprovalDto requestApprovalDto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {		
		requestApprovalDto.setIsApproved(true);
		requestService.approveOrReject(requestApprovalDto);
		redirectAttributes.addFlashAttribute("requestReviewed", "Done! Request has been approved.");
		
		return "redirect:/home";
	}
	
	@PostMapping(value = "/review/submit", params = "action=reject")
	public String processRejectRequest(@ModelAttribute("requestApprovalForm") RequestApprovalDto requestApprovalDto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		requestApprovalDto.setIsApproved(false);
		requestService.approveOrReject(requestApprovalDto);
		redirectAttributes.addFlashAttribute("requestReviewed", "Done! Request has been rejected.");
		
		return "redirect:/home";
	}

}
