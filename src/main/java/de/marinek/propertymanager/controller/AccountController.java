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
import de.marinek.propertymanager.repository.AccountRepository;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountRepository repo;

	@RequestMapping("/show/{id}")
	public String showAccount(@PathVariable("id") Long id, Model model) {
		Optional<AccountDTO> account = repo.findById(id);
		model.addAttribute("account", account.get());
		return "views/account/account";
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
