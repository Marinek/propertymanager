package de.marinek.propertymanager.controller;

import java.util.Arrays;
import java.util.List;

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
import de.marinek.propertymanager.domain.property.ApartmentDTO;
import de.marinek.propertymanager.repository.ApartmentRepository;
import de.marinek.propertymanager.repository.BookingAccountRepository;
import de.marinek.propertymanager.repository.BusinessPeriodRepository;
import de.marinek.propertymanager.repository.BusinessPlanRepository;

@Controller
public class IndexController {

	@Autowired
	private BusinessPeriodRepository businessPlanRepo;
	
	@Autowired
	private BusinessPlanRepository businessplanRepo;
	
	
	@Autowired
	private BookingAccountRepository businessAccountRepo;
	
	@Autowired
	private ApartmentRepository apartmentRepo;

	@GetMapping("/")
	@PreAuthorize(value = "")
	public String fillIndexModel(ModelAndView model) {

		if(businessPlanRepo.count() == 0) {
			{
				;
				
				List<BookingAccount> asList = Arrays.asList(
						BookingAccount.createExpense("Wasser", DistributionKey.A_PP),
						BookingAccount.createExpense("Heizung / Strom", DistributionKey.B_AREA),
						BookingAccount.createExpense("Entwässerung incl. Niederschlagsw. Beb. Fl.", DistributionKey.A_PP),
						BookingAccount.createExpense("Müllabfuhr", DistributionKey.C_EQUAL),
						BookingAccount.createExpense("Straßenreinigung", DistributionKey.C_EQUAL),
						BookingAccount.createExpense("Gebäudeversicherung", DistributionKey.D_ASSETS),
						BookingAccount.createExpense("Kontoführung", DistributionKey.C_EQUAL),
						BookingAccount.createExpense("Wartung Heizung", DistributionKey.C_EQUAL),
						BookingAccount.createExpense("Schornsteinfeger", DistributionKey.C_EQUAL),
						BookingAccount.createExpense("Instandhaltungsrücklage", DistributionKey.C_EQUAL),
						BookingAccount.createExpense("Bargeld", DistributionKey.C_EQUAL)
						);
				
				businessAccountRepo.saveAll(asList);
			}
			
			{
				
				ApartmentDTO app = new ApartmentDTO();
				app.setName("Oben");
				app.setShares(286.85);
				app.setArea(76.0);
				
				OwnerDTO owner = new OwnerDTO();
				owner.setName("Unten");
				owner.setSurname("Eigentümer");
				
				app.setOwner(owner);
				
				apartmentRepo.save(app);
			}
				
				ApartmentDTO app = new ApartmentDTO();
				app.setName("Unten");
				app.setShares(361.53);
				app.setArea(93.0);
				
				OwnerDTO owner = new OwnerDTO();
				owner.setName("Unten");
				owner.setSurname("Eigentümer");
				
				app.setOwner(owner);
				
				apartmentRepo.save(app);
			
			
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setYear(2019);
				periodDTO.addAppartment(app, 2);
				businessPlanRepo.save(periodDTO);
			}
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setYear(2020);
				
				periodDTO.addAppartment(app, 2);
				
				businessPlanRepo.save(periodDTO);

				{
					BudgetPlanDTO plan = new BudgetPlanDTO();

					plan.setBookingAccount(BookingAccount.createExpense("Wasser", DistributionKey.A_PP));
					plan.setBudget(555.0);
					plan.setExternReference("343223423423");
					
					CreditorDTO partner = new CreditorDTO();
					
					partner.setIban("DE54426501500003255080");
					partner.setName("Wasserlieferant");
					
					plan.setPartner(partner);

					periodDTO.addBudgetPlan(plan);
					
					businessplanRepo.save(plan);
				}
				{
					BudgetPlanDTO plan = new BudgetPlanDTO();
					
					plan.setBookingAccount(BookingAccount.createExpense("Heizung / Strom", DistributionKey.B_AREA));
					plan.setBudget(4321.0);
					plan.setExternReference("X4654646");
					
					CreditorDTO partner = new CreditorDTO();
					
					partner.setIban("DE06506400150235108800");
					partner.setName("Heizung");
					
					plan.setPartner(partner);
					
					periodDTO.addBudgetPlan(plan);
					businessplanRepo.save(plan);
				}
				{
					BudgetPlanDTO plan = new BudgetPlanDTO();
					
					plan.setBookingAccount(BookingAccount.createExpense("Müllabfuhr", DistributionKey.C_EQUAL));
					plan.setBudget(555.0);
					plan.setExternReference("XS 123/123/123");
					
					CreditorDTO partner = new CreditorDTO();
					
					partner.setIban("DE29426501500030000079");
					partner.setName("Stadt Waltrop");
					
					plan.setPartner(partner);
					plan.setNote("Neue Mülltonnen angeschafft.");
					
					periodDTO.addBudgetPlan(plan);
					businessplanRepo.save(plan);
				}
				{
					BudgetPlanDTO plan = new BudgetPlanDTO();
					
					plan.setBookingAccount(BookingAccount.createIncome("Vorrauszahlung", DistributionKey.PM_INCOME));
					plan.setBudget(195.0);
					plan.setExternReference("Eigentümer A");
					
					OwnerDTO partner = new OwnerDTO();
					
					partner.setIban("DE21360501050008632127");
					partner.setName("Mustermann");
					partner.setSurname("Max");
					
					plan.setPartner(partner);
					plan.setNote("Seit 1.1.2019");
					
					periodDTO.addBudgetPlan(plan);
					
					ApartmentDTO app2 = new ApartmentDTO();
					app2.setName("Mitte");
					app2.setShares(351.62);
					app2.setArea(93.0);
					
					app2.setOwner(partner);
					
					apartmentRepo.save(app2);
					businessplanRepo.save(plan);
				}
			}
			{
				PeriodDTO periodDTO = new PeriodDTO();
				periodDTO.setYear(2017);
				
				periodDTO.addAppartment(app, 2);
				
				businessPlanRepo.save(periodDTO);

			}
		}

		model.addObject("periods", businessPlanRepo.findAll(Sort.by(Sort.Direction.DESC, "year")));

		return "views/index";
	}


}
