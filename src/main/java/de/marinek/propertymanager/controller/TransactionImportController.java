package de.marinek.propertymanager.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.marinek.propertymanager.components.TransactionImportComponent;
import de.marinek.propertymanager.domain.account.TransactionDTO;

@Controller
@RequestMapping("/account/import")
public class TransactionImportController {

	@Autowired
	private TransactionImportComponent importer;
	
	@GetMapping
	public String listUploadedFiles(Model model) throws IOException {
		
		return "views/account/account_import_transaction";
	}

	@PostMapping
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		List<TransactionDTO> loadObjectList = importer.loadTransactions(file);
		
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		
		redirectAttributes.addFlashAttribute("items", loadObjectList);

		return "redirect:/account/import";
	}


}