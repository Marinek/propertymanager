package de.marinek.propertymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.repository.BookingAccountRepository;

@Controller
@RequestMapping("accounting")
public class BookingAccountController {
	
	@Autowired
	private BookingAccountRepository repo;

	@RequestMapping("")
	public String listBookingAccounts(Model model) {
		Iterable<BookingAccount> accounts = repo.findAll();
		
		model.addAttribute("items", accounts);
		
		return "views/accounting/list";
	}
	
}
