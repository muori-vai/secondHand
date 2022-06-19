package com.siw.secondHand.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siw.secondHand.model.Prodotto;
import com.siw.secondHand.service.ProdottoService;

@Component
public class ProdottoValidator implements Validator {

	@Autowired
	ProdottoService prodottoService;
	
	@Override
	public boolean supports(Class<?> prodottoClass) {
		return Prodotto.class.equals(prodottoClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.prodottoService.alreadyExists((Prodotto) target)) {
			errors.reject("prodotto.duplicato");
		}
		if(this.prodottoService.illegalSottocategoria((Prodotto) target)) {
			errors.reject("prodotto.illegalSottocategoria");
		}
	}
}
