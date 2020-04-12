package de.marinek.propertymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marinek.propertymanager.controller.generic.CrudController;
import de.marinek.propertymanager.domain.property.ApartmentDTO;
import de.marinek.propertymanager.repository.ApartmentRepository;

@Controller
@RequestMapping("apartments")
public class ApartmentController extends CrudController<ApartmentDTO, ApartmentRepository> {

	@Autowired
	private ApartmentRepository apartmentRepo;
	
	@Override
	protected ApartmentRepository getRepository() {
		return apartmentRepo;
	}

	@Override
	protected String getView() {
		return "property/apartment";
	}
}
