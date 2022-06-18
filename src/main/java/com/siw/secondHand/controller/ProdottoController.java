package com.siw.secondHand.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.siw.secondHand.controller.validator.ProdottoValidator;
import com.siw.secondHand.model.Prodotto;
import com.siw.secondHand.service.ProdottoService;

@Controller
public class ProdottoController {

	@Autowired
	private ProdottoService prodottoService;

	@Autowired
	private ProdottoValidator prodottoValidator;

	@PostMapping("/prodotto")
	public String addProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto, BindingResult bindingResult,
			Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {

		prodottoValidator.validate(prodotto, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			
			fileName = fileName.replaceAll("\\s+", ""); //per levare gli spazi
			
			prodotto.setFoto(fileName);

			Prodotto prodottoSalvato = prodottoService.save(prodotto);

			String uploadDir = "prodotto-foto/" + prodottoSalvato.getId();

			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

			model.addAttribute("prodotto", prodotto);
			
			return "prodotto.html";
		}

		return "prodottoForm.html";
	}

//	@PostMapping("/prodotto") FUNZIONE VECCHIA
//	public String addProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto, BindingResult bindingResult,
//			Model model) {
//
//		prodottoValidator.validate(prodotto, bindingResult);
//
//		if (!bindingResult.hasErrors()) {
//			prodottoService.save(prodotto);
//			model.addAttribute("prodotto", prodotto);
//
//			return "prodotto.html";
//		}
//
//		return "prodottoForm.html";
//	}

	@GetMapping("/prodottos")
	public String getProdottos(Model model) {
		List<Prodotto> prodottos = prodottoService.findAll();
		model.addAttribute("prodottos", prodottos);

		return "prodottos.html";
	}

	@GetMapping("/prodotto/{id}")
	public String getProdotto(@PathVariable("id") Long id, Model model) {
		Prodotto prodotto = prodottoService.findById(id);
		model.addAttribute("prodotto", prodotto);

		return "prodotto.html";
	}

	@GetMapping("/prodottoForm")
	public String getProdottoForm(Model model) {
		model.addAttribute("prodotto", new Prodotto());

		return "prodottoForm.html";
	}

	@GetMapping("/deleteProdotto/{id}")
	public String deleteProdotto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("prodotto", this.prodottoService.findById(id));

		return "deleteProdotto.html";
	}

	@Transactional
	@GetMapping("/confirmDeleteProdotto/{id}")
	public String confirmDeleteProdotto(@PathVariable("id") Long id,
			/* @RequestParam(value="action", required=true) String action, */ Model model) {
		/*
		 * if(action.equals("Elimina")) { this.prodottoService.deleteById(id); }
		 */

		this.prodottoService.deleteById(id);

		model.addAttribute("prodottos", prodottoService.findAll());

		return "prodottos.html";
	}
}