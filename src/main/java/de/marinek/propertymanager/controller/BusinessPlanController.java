package de.marinek.propertymanager.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.partner.PartnerDTO;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import de.marinek.propertymanager.domain.plan.PeriodDTO;
import de.marinek.propertymanager.repository.BookingAccountRepository;
import de.marinek.propertymanager.repository.BusinessPeriodRepository;
import de.marinek.propertymanager.repository.BusinessPlanRepository;
import de.marinek.propertymanager.repository.PartnerRepository;

@Controller
@RequestMapping("plan")
public class BusinessPlanController {

	@Autowired
	private BusinessPeriodRepository repo;

	@Autowired
	private BusinessPlanRepository planRepo;

	@Autowired
	private PartnerRepository partnerRepo;

	@Autowired
	private BookingAccountRepository bookingAccountRepo;

	@RequestMapping("/show/{id}")
	public String showAccount(@PathVariable("id") Long id, Model model) {
		Optional<PeriodDTO> period = repo.findById(id);

		model.addAttribute("period", period.get());

		return "views/plans/plan";
	}

	@RequestMapping("/add/{id}")
	public String addBudget(@PathVariable("id") Long id, Model model) {
		Optional<PeriodDTO> period = repo.findById(id);
		Iterable<PartnerDTO> partnerList = partnerRepo.findAll();
		Iterable<BookingAccount> bookingAccounts = bookingAccountRepo.findAll();

		BudgetPlanDTO newBudgetPlan = new BudgetPlanDTO();
		newBudgetPlan.setPeriode(period.get());

		model.addAttribute("period", period.get());
		model.addAttribute("budget", newBudgetPlan);
		model.addAttribute("partner", partnerList);
		model.addAttribute("accounts", bookingAccounts);

		return "views/plans/plan_edit";
	}

	@PostMapping("/add/{id}")
	public String saveBudget(@Valid BudgetPlanDTO b, @PathVariable("id") Long id, BindingResult br, Model model) {
		if(br.hasErrors()) {
			return "views/plans/plan_edit";
		}
		planRepo.save(b);

		return "redirect:/plan/show/" + b.getPeriode().getId();
	}

}
