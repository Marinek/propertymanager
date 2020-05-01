package de.marinek.propertymanager.controller;

import java.util.Iterator;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.domain.account.AccountDTO;
import de.marinek.propertymanager.domain.account.TransactionDTO;
import de.marinek.propertymanager.repository.AccountRepository;

@Controller
@RequestMapping("/account")
public class BankAccountController {

	@Autowired
	private AccountRepository repo;

	@RequestMapping("/show/{id}")
	public String showAccount(@PathVariable("id") Long id,@Param("filter") String filter, Model model) {
		Optional<AccountDTO> account = repo.findById(id);
		
		filterTransactions(filter, account);
		
		model.addAttribute("account", account.get());
		return "views/account/account_edit";
	}

	private void filterTransactions(String filter, Optional<AccountDTO> account) {
		Iterator<TransactionDTO> iterator = account.get().getTransactions().iterator();
		
		if(StringUtils.isEmpty(filter) || filter.equals("ALL")) {
			return;
		} else  {
			while(iterator.hasNext()) {
				TransactionDTO next = iterator.next();
				
				if((next.getBudgetPlan() != null && StringUtils.equals(filter, "OPEN")) ^ 
						(next.getBudgetPlan() == null && StringUtils.equals(filter, "CLOSED"))) {
					iterator.remove();
				}
			}
		} 
	}

	@RequestMapping("/edit/{id}")
	public String editAccount(@PathVariable("id") Long id, Model model) {
		Optional<AccountDTO> account = repo.findById(id);

		model.addAttribute("account", account);

		return "views/account/account_edit";
	}

	@RequestMapping("/add")
	public String addAccount(Model model) {

		model.addAttribute("account", new AccountDTO());

		return "views/account/account_edit";
	}

	@RequestMapping("")
	public String showUserForm(Model model) {

		model.addAttribute("accounts", repo.findAll());
		return "views/account/account";
	}

	@PostMapping("/edit")
	public String saveAccount(@ModelAttribute AccountDTO account) {
		repo.save(account);
		return "redirect:/account";
	}

}
