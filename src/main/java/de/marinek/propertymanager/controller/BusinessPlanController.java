package de.marinek.propertymanager.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.partner.PartnerDTO;
import de.marinek.propertymanager.domain.plan.ApartmentPeriod;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import de.marinek.propertymanager.domain.plan.PeriodDTO;
import de.marinek.propertymanager.domain.property.ApartmentDTO;
import de.marinek.propertymanager.repository.ApartmentRepository;
import de.marinek.propertymanager.repository.AppartmentPeriodeRepository;
import de.marinek.propertymanager.repository.BookingAccountRepository;
import de.marinek.propertymanager.repository.BusinessPeriodRepository;
import de.marinek.propertymanager.repository.BusinessPlanRepository;
import de.marinek.propertymanager.repository.PartnerRepository;

@Controller
@RequestMapping("plan")
public class BusinessPlanController {

	@Autowired
	private ApartmentRepository propertyRepo;
	
	
	@Autowired
	private BusinessPeriodRepository repo;

	@Autowired
	private BusinessPlanRepository planRepo;

	@Autowired
	private PartnerRepository partnerRepo;

	@Autowired
	private BookingAccountRepository bookingAccountRepo;

	@Autowired
	private AppartmentPeriodeRepository apartmentPeriodRepo;

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
	
	@RequestMapping("edit/budget/{id}")
	public String editBudget(@PathVariable("id") Long bId, Model model) {
		
		BudgetPlanDTO budgetPlan = planRepo.findById(bId).get();
		
		addBudget(budgetPlan.getPeriode().getId(), model);
		model.addAttribute("budget", budgetPlan);
		
		return "views/plans/plan_edit";		
	}
	
	@RequestMapping("/add/apartment/{id}")
	public String addApartment(@PathVariable("id") Long periodId, Model model) {
		Optional<PeriodDTO> period = repo.findById(periodId);

		Iterable<ApartmentDTO> appartments = propertyRepo.findNotAssociated(periodId);
		
		ApartmentPeriod apartmentPeriod = new ApartmentPeriod();
		apartmentPeriod.setPeriode(period.get());

		model.addAttribute("periode", period.get());
		model.addAttribute("apartmentperiode", apartmentPeriod);
		model.addAttribute("apartments", appartments);

		return "views/plans/plan_add_property";
	}
	
	@PostMapping("/add/apartment/")
	public String addApartment(@Valid ApartmentPeriod a, BindingResult br, Model model) {
		if(br.hasErrors()) {
			return "views/plans/plan_edit";
		}
		
		apartmentPeriodRepo.save(a);

		return "redirect:/plan/show/" + a.getPeriode().getId();
	}

}
