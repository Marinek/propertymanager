package de.marinek.propertymanager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.domain.plan.PeriodDTO;
import de.marinek.propertymanager.repository.BusinessPlanRepository;

@Controller
@RequestMapping("plan")
public class BusinessPlanController {

	@Autowired
	private BusinessPlanRepository repo;

	@RequestMapping("/show/{id}")
	public String showAccount(@PathVariable("id") Long id, Model model) {
		Optional<PeriodDTO> period = repo.findById(id);
		
		model.addAttribute("period", period.get());
		
		return "views/plans/plan";
	}

}
