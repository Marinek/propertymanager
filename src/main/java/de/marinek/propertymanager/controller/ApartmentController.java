package de.marinek.propertymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.repository.ApartmentRepository;

@Controller
@RequestMapping("apartments")
public class ApartmentController {

	@Autowired
	private ApartmentRepository apartmentRepo;
	
	@RequestMapping("")
	public String showUserForm(Model model) {

		model.addAttribute("appartments", apartmentRepo.findAll());
		return "views/property/apartment";
	}
}
