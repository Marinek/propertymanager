package de.marinek.propertymanager.controller.generic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class CrudController<DTO, T extends CrudRepository<DTO, Long>> {

	private static final String VIEWS = "views/";
	
	protected abstract T getRepository();

	protected abstract String getView();
	
	@RequestMapping("")
	public String showRoot(Model model) {
		model.addAttribute("items", getRepository().findAll());
		
		return VIEWS + getView();
	}
}
