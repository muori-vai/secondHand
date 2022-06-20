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

import com.siw.secondHand.controller.validator.LuogoValidator;
import com.siw.secondHand.model.Luogo;
import com.siw.secondHand.service.LuogoService;

@Controller
public class LuogoController {

	@Autowired
	private LuogoService luogoService;
	
	@Autowired
	private LuogoValidator luogoValidator;

	@PostMapping("/luogo")
	public String addLuogo(@Valid @ModelAttribute("luogo") Luogo luogo, BindingResult bindingResult,
			Model model) throws IOException {

		luogoValidator.validate(luogo, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			Luogo luogoSalvato = luogoService.save(luogo); //questo save è diverso dal save del progetto Catering
			
			model.addAttribute("luogo", luogoSalvato);
			
			return "redirect:/luogo/"+luogoSalvato.getId();
		}

		return "luogoForm.html";
	}

	@GetMapping("/luogo/all")
	public String getLuogos(Model model) {
		List<Luogo> luogos = luogoService.findAll();
		model.addAttribute("luogos", luogos);

		return "luogos.html";
	}

	@GetMapping("/luogo/{id}")
	public String getLuogo(@PathVariable("id") Long id, Model model) {
		Luogo luogo = luogoService.findById(id);
		model.addAttribute("luogo", luogo);

		return "luogo.html";
	}

	@GetMapping("/luogo/form")
	public String getLuogoForm(Model model) {
		model.addAttribute("luogo", new Luogo());

		return "luogoForm.html";
	}

	@GetMapping("/luogo/delete/{id}")
	public String deleteLuogo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("luogo", this.luogoService.findById(id));

		return "luogoDelete.html";
	}

	@GetMapping("/luogo/delete/confirm/{id}")
	public String confirmDeleteLuogo(@PathVariable("id") Long id, Model model) {
		this.luogoService.deleteById(id);

		model.addAttribute("luogos", luogoService.findAll());

		return "luogos.html";
	}
	
	@GetMapping("/luogo/edit/form/{id}")
	public String editLuogoForm(@PathVariable Long id, Model model) {
		model.addAttribute("luogo", luogoService.findById(id));
		
		return "luogoEditForm.html";
	}

	@PostMapping("/luogo/edit/{id}")
	public String editLuogo(@Valid @ModelAttribute("luogo") Luogo luogo, BindingResult bindingResult, @PathVariable Long id,
			Model model) {

		Luogo vecchioLuogo = this.luogoService.findById(id);

		if (!vecchioLuogo.equals(luogo))
			this.luogoValidator.validate(luogo, bindingResult);

		if (!bindingResult.hasErrors()) {

			vecchioLuogo.setNome(luogo.getNome());
			
			Luogo luogoSalvata = this.luogoService.save(vecchioLuogo);
		
			model.addAttribute("luogo", luogoSalvata);
			
			return "redirect:/luogo/"+luogoSalvata.getId();
		}

		return "luogoEditForm.html";
	}
}