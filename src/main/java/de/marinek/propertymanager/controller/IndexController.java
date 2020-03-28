package de.marinek.propertymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.marinek.propertymanager.repository.AccountRepository;

@Controller
public class IndexController {
	
	@Autowired
	private AccountRepository repo;

	@GetMapping("/")
	@PreAuthorize(value = "")
	public String index() {
		
		return "views/index";
	}
	
	@GetMapping("/test")
	@PreAuthorize(value = "")
	public String index2(Model model) {
		
		model.addAttribute("count", "Test: " + repo.count());
		
		return "views/index";
	}
	
}
