package com.siw.secondHand.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siw.secondHand.model.Categoria;
import com.siw.secondHand.service.CategoriaService;

@Component
public class CategoriaValidator implements Validator {

	@Autowired
	CategoriaService categoriaService;
	
	@Override
	public boolean supports(Class<?> categoriaClass) {
		return Categoria.class.equals(categoriaClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.categoriaService.alreadyExists((Categoria) target)) {
			errors.reject("categoria.duplicato");
		}
	}
}
