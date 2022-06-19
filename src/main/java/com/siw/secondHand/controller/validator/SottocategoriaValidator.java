package com.siw.secondHand.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siw.secondHand.model.Categoria;
import com.siw.secondHand.model.Sottocategoria;
import com.siw.secondHand.service.SottocategoriaService;

@Component
public class SottocategoriaValidator implements Validator {

	@Autowired
	SottocategoriaService sottocategoriaService;
	
	@Override
	public boolean supports(Class<?> sottocategoriaClass) {
		return Categoria.class.equals(sottocategoriaClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.sottocategoriaService.alreadyExists((Sottocategoria) target)) {
			errors.reject("sottocategoria.duplicato");
		}
	}
}
