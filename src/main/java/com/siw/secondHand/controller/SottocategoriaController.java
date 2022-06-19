package com.siw.secondHand.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.siw.secondHand.controller.validator.SottocategoriaValidator;
import com.siw.secondHand.model.Sottocategoria;
import com.siw.secondHand.service.CategoriaService;
import com.siw.secondHand.service.SottocategoriaService;

@Controller
public class SottocategoriaController {

	@Autowired
	private SottocategoriaService sottocategoriaService;

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private SottocategoriaValidator sottocategoriaValidator;

	@PostMapping("/sottocategoria")
	public String addSottocategoria(@Valid @ModelAttribute("sottocategoria") Sottocategoria sottocategoria, BindingResult bindingResult,
			Model model) throws IOException {

		sottocategoriaValidator.validate(sottocategoria, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			Sottocategoria sottocategoriaSalvato = sottocategoriaService.save(sottocategoria); //questo save è diverso dal save del progetto Catering
			
			model.addAttribute("sottocategoria", sottocategoriaSalvato);

			return "sottocategoria.html";
		}

		return "sottocategoriaForm.html";
	}

	@GetMapping("/sottocategorias")
	public String getSottocategorias(Model model) {
		List<Sottocategoria> sottocategorias = sottocategoriaService.findAll();
		model.addAttribute("sottocategorias", sottocategorias);

		return "sottocategorias.html";
	}

	@GetMapping("/sottocategoria/{id}")
	public String getSottocategoria(@PathVariable("id") Long id, Model model) {
		Sottocategoria sottocategoria = sottocategoriaService.findById(id);
		model.addAttribute("sottocategoria", sottocategoria);

		return "sottocategoria.html";
	}

	@GetMapping("/sottocategoriaForm")
	public String getSottocategoriaForm(Model model) {
		model.addAttribute("sottocategoria", new Sottocategoria());
		model.addAttribute("categorias", this.categoriaService.findAll());

		return "sottocategoriaForm.html";
	}

	@GetMapping("/deleteSottocategoria/{id}")
	public String deleteSottocategoria(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sottocategoria", this.sottocategoriaService.findById(id));

		return "deleteSottocategoria.html";
	}

	@GetMapping("/confirmDeleteSottocategoria/{id}")
	public String confirmDeleteSottocategoria(@PathVariable("id") Long id,
			/* @RequestParam(value="action", required=true) String action, */ Model model) {
		/*
		 * if(action.equals("Elimina")) { this.sottocategoriaService.deleteById(id); }
		 */

		this.sottocategoriaService.deleteById(id);

		model.addAttribute("sottocategorias", sottocategoriaService.findAll());

		return "sottocategorias.html";
	}
}