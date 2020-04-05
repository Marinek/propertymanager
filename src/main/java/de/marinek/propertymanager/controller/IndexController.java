package de.marinek.propertymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.partner.CreditorDTO;
import de.marinek.propertymanager.domain.partner.OwnerDTO;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import de.marinek.propertymanager.domain.plan.DistributionKey;
import de.marinek.propertymanager.domain.plan.PeriodDTO;
import de.marinek.propertymanager.domain.property.Apartment;
import de.marinek.propertymanager.repository.ApartmentRepository;
import de.marinek.propertymanager.repository.BusinessPlanRepository;

@Controller
public class IndexController {

	@Autowired
	private BusinessPlanRepository businessPlanRepo;
	
	@Autowired
	private ApartmentRepository apartmentRepo;

	@GetMapping("/")
	@PreAuthorize(value = "")
	public String fillIndexModel(ModelAndView model) {

		if(businessPlanRepo.count() == 0) {
			{
				
				Apartment app = new Apartment();
				app.setName("Oben");
				app.setPersons(2);
				app.setShares(286.85);
				app.setArea(76.0);
				
				OwnerDTO owner = new OwnerDTO();
				owner.setName("Unten");
				owner.setSurname("Eigentümer");
				
				app.setOwner(owner);
				
				apartmentRepo.save(app);
			}
			{
				
				Apartment app = new Apartment();
				app.setName("Unten");
				app.setPersons(2);
				app.setShares(361.53);
				app.setArea(93.0);
				
				OwnerDTO owner = new OwnerDTO();
				owner.setName("Unten");
				owner.setSurname("Eigentümer");
				
				app.setOwner(owner);
				
				apartmentRepo.save(app);
			}
			
			
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setYear(2019);

				businessPlanRepo.save(periodDTO);
			}
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setYear(2020);
				
				businessPlanRepo.save(periodDTO);

				{
					BudgetPlanDTO plan = new BudgetPlanDTO();

					plan.setBookingAccount(BookingAccount.createExpense("Wasser", DistributionKey.A_PP));
					plan.setBudget(555.0);
					plan.setExternReference("343223423423");
					
					CreditorDTO partner = new CreditorDTO();
					
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
					
					CreditorDTO partner = new CreditorDTO();
					
					partner.setIban("DE89370412350123413000");
					partner.setName("Heizung und Strom GmbH");
					
					plan.setPartner(partner);
					
					periodDTO.addBudgetPlan(plan);
				}
				{
					BudgetPlanDTO plan = new BudgetPlanDTO();
					
					plan.setBookingAccount(BookingAccount.createExpense("Müllabfuhr", DistributionKey.C_EQUAL));
					plan.setBudget(555.0);
					plan.setExternReference("XS 123/123/123");
					
					CreditorDTO partner = new CreditorDTO();
					
					partner.setIban("DE8937041212356000");
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
					
					OwnerDTO partner = new OwnerDTO();
					
					partner.setIban("DE21360501050001234567");
					partner.setName("Mustermann");
					partner.setSurname("Max");
					
					plan.setPartner(partner);
					plan.setNote("Seit 1.1.2019");
					
					periodDTO.addBudgetPlan(plan);
					
					Apartment app = new Apartment();
					app.setName("Mitte");
					app.setPersons(1);
					app.setShares(351.62);
					app.setArea(93.0);
					
					app.setOwner(partner);
					
					apartmentRepo.save(app);
				}
			}
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setYear(2017);
				businessPlanRepo.save(periodDTO);

			}
		}

		model.addObject("periods", businessPlanRepo.findAll(Sort.by(Sort.Direction.DESC, "year")));

		return "views/index";
	}


}
