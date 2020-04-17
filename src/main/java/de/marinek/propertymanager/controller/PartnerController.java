package de.marinek.propertymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.domain.partner.PartnerDTO;
import de.marinek.propertymanager.repository.PartnerRepository;

@Controller
@RequestMapping("partner")
public class PartnerController {

	@Autowired
	private PartnerRepository partnerRepo;
	
	@GetMapping("list/{type}")
	private String listPartner(@PathVariable("type") String type, Model model) {
		Iterable<PartnerDTO> partner = partnerRepo.findByType(type);
		
		model.addAttribute("items", partner);
		
		return "views/partner/list";
	}
}
