package com.siw.secondHand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.siw.secondHand.controller.validator.CredentialsValidator;
import com.siw.secondHand.controller.validator.UserValidator;
import com.siw.secondHand.model.Credentials;
import com.siw.secondHand.model.User;
import com.siw.secondHand.service.CredentialsService;
import com.siw.secondHand.service.LuogoService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private LuogoService luogoService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("luogos", this.luogoService.findAll());
		return "registerUser";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm(Model model) {
		return "loginForm";
	}

	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	public String showLoginFormError(Model model) {
		return "loginFormError";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("nome", credentials.getUser().getNome());
		//purtroppo senza questo l'autenticazione non va via e quindi il logout non funziona (anche se dovrebbe essere automatico)
		SecurityContextHolder.getContext().setAuthentication(null);
		
		return "goodbye";
	}
	
	//pagina per confermare di voler uscire dall'account
	@RequestMapping(value = "/askLogout", method = RequestMethod.GET)
	public String showLogout(Model model) {
		return "askLogout.html";
	}

	//precedentemente /default, pagina dopo un login di successo
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String defaultAfterLogin(Model model) {
		return "home";
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			Model model) {

		// validate user and credentials fields
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// if neither of them had invalid contents, store the User and the Credentials
		// into the DB
		if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			// set the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "registrationSuccessful";
		}
		
		model.addAttribute("luogos", this.luogoService.findAll());
		return "registerUser";
	}
}
