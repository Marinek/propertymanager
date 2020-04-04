package de.marinek.propertymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.accounting.DistributionKey;
import de.marinek.propertymanager.domain.accounting.PartnerDTO;
import de.marinek.propertymanager.domain.accounting.PeriodDTO;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import de.marinek.propertymanager.repository.BusinessPlanRepository;

@Controller
public class IndexController {

	@Autowired
	private BusinessPlanRepository businessPlanRepo;

	@GetMapping("/")
	@PreAuthorize(value = "")
	public String fillIndexModel(ModelAndView model) {

		if(businessPlanRepo.count() == 0) {
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setName("2019");

				businessPlanRepo.save(periodDTO);
			}
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setName("2020");
				businessPlanRepo.save(periodDTO);

				{
					BudgetPlanDTO plan = new BudgetPlanDTO();

					plan.setBookingAccount(BookingAccount.createExpense("Wasser", DistributionKey.A_PP));
					plan.setBudget(555.0);
					plan.setExternReference("343223423423");
					
					PartnerDTO partner = new PartnerDTO();
					
					partner.setIban("DE89370400440532013000");
					partner.setName("Wasserlieferant");
					
					plan.setPartner(partner);

					periodDTO.addBudgetPlan(plan);
				}
				{
					BudgetPlanDTO plan = new BudgetPlanDTO();
					
					plan.setBookingAccount(BookingAccount.createExpense("Heizung / Strom", DistributionKey.B_AREA));
					plan.setBudget(4321.0);
					plan.setExternReference("X4654646");
					
					PartnerDTO partner = new PartnerDTO();
					
					partner.setIban("DE89370412350123413000");
					partner.setName("Heizung und Strom");
					
					plan.setPartner(partner);
					
					periodDTO.addBudgetPlan(plan);
				}
				{
					BudgetPlanDTO plan = new BudgetPlanDTO();
					
					plan.setBookingAccount(BookingAccount.createExpense("Müllabfuhr", DistributionKey.C_EQUAL));
					plan.setBudget(555.0);
					plan.setExternReference("XS 123/123/123");
					
					PartnerDTO partner = new PartnerDTO();
					
					partner.setIban("XS 123/123/123");
					partner.setName("Stadt Waltrop");
					
					plan.setPartner(partner);
					plan.setNote("Neue Mülltonnen angeschafft.");
					
					periodDTO.addBudgetPlan(plan);
				}
				{
					BudgetPlanDTO plan = new BudgetPlanDTO();
					
					plan.setBookingAccount(BookingAccount.createIncome("Vorrauszahlung", DistributionKey.PM_INCOME));
					plan.setBudget(195.0);
					plan.setExternReference("Eigentümer A");
					
					PartnerDTO partner = new PartnerDTO();
					
					partner.setIban("DE213605010500012345678");
					partner.setName("XXX");
					
					plan.setPartner(partner);
					plan.setNote("Festgelegt seit 1.1.2019");
					
					periodDTO.addBudgetPlan(plan);
				}
			}
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setName("2017");
				businessPlanRepo.save(periodDTO);

			}
		}

		model.addObject("periods", businessPlanRepo.findAll(Sort.by(Sort.Direction.DESC, "name")));

		return "views/index";
	}


}
