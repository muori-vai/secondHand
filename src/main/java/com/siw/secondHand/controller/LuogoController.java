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
			
			return "luogo.html";
		}

		return "luogoForm.html";
	}

	@GetMapping("/luogos")
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

	@GetMapping("/luogoForm")
	public String getLuogoForm(Model model) {
		model.addAttribute("luogo", new Luogo());

		return "luogoForm.html";
	}

	@GetMapping("/deleteLuogo/{id}")
	public String deleteLuogo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("luogo", this.luogoService.findById(id));

		return "deleteLuogo.html";
	}

	@GetMapping("/confirmDeleteLuogo/{id}")
	public String confirmDeleteLuogo(@PathVariable("id") Long id, Model model) {
		this.luogoService.deleteById(id);

		model.addAttribute("luogos", luogoService.findAll());

		return "luogos.html";
	}
}