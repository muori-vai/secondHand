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

import com.siw.secondHand.controller.validator.CategoriaValidator;
import com.siw.secondHand.model.Categoria;
import com.siw.secondHand.service.CategoriaService;

@Controller
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private CategoriaValidator categoriaValidator;

	@PostMapping("/categoria")
	public String addCategoria(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult bindingResult,
			Model model) throws IOException {

		categoriaValidator.validate(categoria, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			Categoria categoriaSalvata = categoriaService.save(categoria); //questo save è diverso dal save del progetto Catering

			//categoriaSalvato mi servirà dopo
			
			model.addAttribute("categoria", categoriaSalvata);
			
			return "redirect:/categoria/"+categoriaSalvata.getId();
		}

		return "categoriaForm.html";
	}

	@GetMapping("/categoria/all")
	public String getCategorias(Model model) {
		List<Categoria> categorias = categoriaService.findAll();
		model.addAttribute("categorias", categorias);

		return "categorias.html";
	}

	@GetMapping("/categoria/{id}")
	public String getCategoria(@PathVariable("id") Long id, Model model) {
		Categoria categoria = categoriaService.findById(id);
		model.addAttribute("categoria", categoria);

		return "categoria.html";
	}

	@GetMapping("admin/categoria/form")
	public String getCategoriaForm(Model model) {
		model.addAttribute("categoria", new Categoria());

		return "categoriaForm.html";
	}

	@GetMapping("admin/categoria/delete/{id}")
	public String deleteCategoria(@PathVariable("id") Long id, Model model) {
		model.addAttribute("categoria", this.categoriaService.findById(id));

		return "categoriaDelete.html";
	}

	@GetMapping("admin/categoria/delete/confirm/{id}")
	public String confirmDeleteCategoria(@PathVariable("id") Long id,
			/* @RequestParam(value="action", required=true) String action, */ Model model) {
		/*
		 * if(action.equals("Elimina")) { this.categoriaService.deleteById(id); }
		 */

		this.categoriaService.deleteById(id);

		model.addAttribute("categorias", categoriaService.findAll());

		return "categorias.html";
	}
	
	@GetMapping("admin/categoria/edit/form/{id}")
	public String editCategoriaForm(@PathVariable Long id, Model model) {
		model.addAttribute("categoria", categoriaService.findById(id));
		
		return "categoriaEditForm.html";
	}

	@PostMapping("admin/categoria/edit/{id}")
	public String editCategoria(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult bindingResult, @PathVariable Long id,
			Model model) {

		Categoria vecchiaCategoria = this.categoriaService.findById(id);

		if (!vecchiaCategoria.equals(categoria))
			this.categoriaValidator.validate(categoria, bindingResult);

		if (!bindingResult.hasErrors()) {

			vecchiaCategoria.setNome(categoria.getNome());
			vecchiaCategoria.setDescrizione(categoria.getDescrizione());
			
			Categoria categoriaSalvata = this.categoriaService.save(vecchiaCategoria);
		
			model.addAttribute("categoria", categoriaSalvata);
			
			return "redirect:/categoria/"+categoriaSalvata.getId();
		}

		return "categoriaEditForm.html";
	}
}