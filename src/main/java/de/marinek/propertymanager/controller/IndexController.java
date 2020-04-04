package de.marinek.propertymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import de.marinek.propertymanager.domain.accounting.AccountType;
import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.accounting.DistributionKey;
import de.marinek.propertymanager.domain.accounting.PeriodDTO;
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
					BookingAccount bookingAccount = new BookingAccount();

					bookingAccount.setDistributionkey(DistributionKey.A_PP);
					bookingAccount.setType(AccountType.EXPENSE);
					bookingAccount.setName("Wasser");

					periodDTO.addBookingAccount(bookingAccount);
				}
				{
					BookingAccount bookingAccount = new BookingAccount();
					
					bookingAccount.setDistributionkey(DistributionKey.B_AREA);
					bookingAccount.setType(AccountType.EXPENSE);
					bookingAccount.setName("Heizung / Strom");
					
					periodDTO.addBookingAccount(bookingAccount);
				}
				{
					BookingAccount bookingAccount = new BookingAccount();
					
					bookingAccount.setDistributionkey(DistributionKey.C_EQUAL);
					bookingAccount.setType(AccountType.EXPENSE);
					bookingAccount.setName("Müllabfuhr");
					
					periodDTO.addBookingAccount(bookingAccount);
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
