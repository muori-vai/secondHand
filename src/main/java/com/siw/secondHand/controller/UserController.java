package com.siw.secondHand.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.siw.secondHand.controller.validator.UserValidator;
import com.siw.secondHand.model.Credentials;
import com.siw.secondHand.model.User;
import com.siw.secondHand.service.CredentialsService;
import com.siw.secondHand.service.LuogoService;
import com.siw.secondHand.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LuogoService luogoService;
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserValidator userValidator;

//	@PostMapping("/user")
//	public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
//			Model model) throws IOException {
//
//		userValidator.validate(user, bindingResult);
//		
//		if (!bindingResult.hasErrors()) {
//			
//			User userSalvato = userService.saveUser(user); //questo save è diverso dal save del progetto Catering
//
//			//userSalvato mi servirà dopo
//			
//			model.addAttribute("user", userSalvato);
//			
//			return "redirect:/user/"+userSalvato.getId();
//		}
//
//		return "userForm.html";
//	}

	@GetMapping("/user/all")
	public String getUsers(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);

		return "users.html";
	}

	@GetMapping("/user/{id}")
	public String getUser(@PathVariable("id") Long id, Model model) {
		User user = userService.getUser(id);
		model.addAttribute("user", user);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User currentUser = credentials.getUser();
		model.addAttribute("currentUserId", currentUser.getId());

		return "user.html";
	}
	
	@GetMapping("/user/profile")
	public String getUserProfile(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User currentUser = credentials.getUser();

		model.addAttribute("user", currentUser);

		return "user.html";
	}

//	@GetMapping("/user/form")
//	public String getUserForm(Model model) {
//		model.addAttribute("user", new User());
//
//		return "userForm.html";
//	}

//	@GetMapping("/user/delete/{id}")
//	public String deleteUser(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("user", this.userService.getUser(id));
//
//		return "userDelete.html";
//	}

//	@GetMapping("/user/delete/confirm/{id}")
//	public String confirmDeleteUser(@PathVariable("id") Long id,
//			/* @RequestParam(value="action", required=true) String action, */ Model model) {
//		/*
//		 * if(action.equals("Elimina")) { this.userService.deleteById(id); }
//		 */
//
//		this.userService.deleteUser(this.userService.getUser(id));
//
//		model.addAttribute("users", userService.getAllUsers());
//
//		return "users.html";
//	}
	
	@GetMapping("/user/edit/form/{id}")
	public String editUserForm(@PathVariable Long id, Model model) {
		User user = userService.getUser(id);
		model.addAttribute("user", user);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User currentUser = credentials.getUser();
		if (user.equals(currentUser) || credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			model.addAttribute("luogos", this.luogoService.findAll());
			return "userEditForm.html";
		}
		return "unauthorized.html";
	}

	@PostMapping("/user/edit/{id}")
	public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @PathVariable Long id,
			Model model) {

		User vecchioUser = this.userService.getUser(id);

		if (!vecchioUser.equals(user))
			this.userValidator.validate(user, bindingResult);

		if (!bindingResult.hasErrors()) {

			vecchioUser.setNome(user.getNome());
			vecchioUser.setCognome(user.getCognome());
			vecchioUser.setLuogo(user.getLuogo());
			
			User userSalvato = this.userService.saveUser(vecchioUser);
		
			model.addAttribute("user", userSalvato);
			
			return "redirect:/user/"+userSalvato.getId();
		}

		model.addAttribute("luogos", this.luogoService.findAll());
		return "userEditForm.html";
	}
}