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
			
			Sottocategoria sottocategoriaSalvata = sottocategoriaService.save(sottocategoria); //questo save è diverso dal save del progetto Catering
			
			model.addAttribute("sottocategoria", sottocategoriaSalvata);

			return "redirect:/sottocategoria/"+sottocategoriaSalvata.getId();
		}

		return "sottocategoriaForm.html";
	}

	@GetMapping("/sottocategoria/all")
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

	@GetMapping("admin/sottocategoria/form")
	public String getSottocategoriaForm(Model model) {
		model.addAttribute("sottocategoria", new Sottocategoria());
		model.addAttribute("categorias", this.categoriaService.findAll());

		return "sottocategoriaForm.html";
	}

	@GetMapping("admin/sottocategoria/delete/{id}")
	public String deleteSottocategoria(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sottocategoria", this.sottocategoriaService.findById(id));

		return "sottocategoriaDelete.html";
	}

	@GetMapping("admin/sottocategoria/delete/confirm/{id}")
	public String confirmDeleteSottocategoria(@PathVariable("id") Long id,
			/* @RequestParam(value="action", required=true) String action, */ Model model) {
		/*
		 * if(action.equals("Elimina")) { this.sottocategoriaService.deleteById(id); }
		 */

		this.sottocategoriaService.deleteById(id);

		model.addAttribute("sottocategorias", sottocategoriaService.findAll());

		return "sottocategorias.html";
	}
	
	@GetMapping("admin/sottocategoria/edit/form/{id}")
	public String editSottocategoriaForm(@PathVariable Long id, Model model) {
		model.addAttribute("sottocategoria", sottocategoriaService.findById(id));
		model.addAttribute("sottocategorias", this.categoriaService.findAll());
		
		return "sottocategoriaEditForm.html";
	}

	@PostMapping("admin/sottocategoria/edit/{id}")
	public String editSottocategoria(@Valid @ModelAttribute("sottocategoria") Sottocategoria sottocategoria, BindingResult bindingResult, @PathVariable Long id,
			Model model) {

		Sottocategoria vecchiaSottocategoria = this.sottocategoriaService.findById(id);

		if (!vecchiaSottocategoria.equals(sottocategoria))
			this.sottocategoriaValidator.validate(sottocategoria, bindingResult);

		if (!bindingResult.hasErrors()) {

			vecchiaSottocategoria.setNome(sottocategoria.getNome());
			vecchiaSottocategoria.setDescrizione(sottocategoria.getDescrizione());
			
			Sottocategoria sottocategoriaSalvata = this.sottocategoriaService.save(vecchiaSottocategoria);
		
			model.addAttribute("sottocategoria", sottocategoriaSalvata);
			
			return "redirect:/sottocategoria/"+sottocategoriaSalvata.getId();
		}
		
		model.addAttribute("sottocategorias", this.categoriaService.findAll());
		
		return "sottocategoriaEditForm.html";
	}
}