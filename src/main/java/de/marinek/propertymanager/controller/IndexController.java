package de.marinek.propertymanager.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	@PreAuthorize(value = "")
	public String index() {
		
		return "views/index";
	}
	
}
