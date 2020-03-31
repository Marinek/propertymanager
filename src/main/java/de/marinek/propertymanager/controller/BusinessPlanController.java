package de.marinek.propertymanager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.domain.account.AccountDTO;
import de.marinek.propertymanager.domain.accounting.PeriodDTO;
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

	@RequestMapping("/edit/{id}")
	public String editAccount(@PathVariable("id") Long id, Model model) {
		Optional<PeriodDTO> period = repo.findById(id);

		model.addAttribute("period", period);

		return "views/plan/plan_edit";
	}

	@RequestMapping("/add")
	public String addAccount(Model model) {

		model.addAttribute("account", new AccountDTO());

		return "views/plans/plans_edit";
	}

	@RequestMapping("")
	public String showUserForm(Model model) {

		model.addAttribute("accounts", repo.findAll());
		return "views/plans/plan";
	}

	@PostMapping("/edit")
	public String saveAccount(@ModelAttribute PeriodDTO period) {
		repo.save(period);
		return "redirect:/period";
	}
}
