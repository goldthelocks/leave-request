/**
 * 
 */
package com.leave.request.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author eotayde
 *
 */
@Controller
public class UserController {

	@GetMapping("/user")
	public String user(Model model) {
		return "user";
	}
}
