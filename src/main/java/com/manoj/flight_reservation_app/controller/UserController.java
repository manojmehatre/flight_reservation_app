package com.manoj.flight_reservation_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.manoj.flight_reservation_app.entity.User;
import com.manoj.flight_reservation_app.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@RequestMapping(path = "/showLoginPage")
	public String showLoginPage() {
		return "login/login";

	}

	@RequestMapping(path = "/showReg")
	public String showReg() {
		return "login/newRegistration";
	}

	@RequestMapping(path = "/saveReg")
	public String saveReg(@ModelAttribute("user") User user) {
		userRepo.save(user);
		return "login/login";
	}

	@RequestMapping(path = "/verifyLogin")
	public String verifyLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelMap modelMap) {
		User user = userRepo.findByEmail(email);
	if(user!=null) {
		if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
			return "searchFlights";
		} else {
			modelMap.addAttribute("error", "Invalid username/password");
			return "login/login";
		} 
	}else { 
			  modelMap.addAttribute("error","Invalid Username / Password"); 
			  return "login/login"; 
	     }

	}

}
