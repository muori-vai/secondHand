package com.siw.secondHand.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siw.secondHand.model.Categoria;
import com.siw.secondHand.model.Luogo;
import com.siw.secondHand.service.LuogoService;

@Component
public class LuogoValidator implements Validator {

	@Autowired
	LuogoService luogoService;
	
	@Override
	public boolean supports(Class<?> luogoClass) {
		return Categoria.class.equals(luogoClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.luogoService.alreadyExists((Luogo) target)) {
			errors.reject("luogo.duplicato");
		}
	}
}
