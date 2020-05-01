package de.marinek.propertymanager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.domain.plan.PeriodDTO;
import de.marinek.propertymanager.repository.BusinessPeriodRepository;

@Controller
@RequestMapping("invoice")
public class InvoiceController {

	@Autowired
	private BusinessPeriodRepository periodRepo;
	
	@RequestMapping("show/{id}")
	public String showInvoice(@PathVariable("id") Long id, @Param("index") Integer index, Model model) {
		Optional<PeriodDTO> findById = periodRepo.findById(id);
		
		if(index == null) {
			index = 0;
		}
		
		model.addAttribute("periode", findById.get());
		
		model.addAttribute("indexi", index);
		
		return "views/plans/invoice";
	}
}
