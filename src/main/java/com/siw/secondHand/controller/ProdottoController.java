package com.siw.secondHand.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.siw.secondHand.controller.validator.ProdottoValidator;
import com.siw.secondHand.model.Credentials;
import com.siw.secondHand.model.Prodotto;
import com.siw.secondHand.model.User;
import com.siw.secondHand.service.CategoriaService;
import com.siw.secondHand.service.CredentialsService;
import com.siw.secondHand.service.LuogoService;
import com.siw.secondHand.service.ProdottoService;

@Controller
public class ProdottoController {

	@Autowired
	private ProdottoService prodottoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private LuogoService luogoService;

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private ProdottoValidator prodottoValidator;

	// ho messo anche @RequestParam("image") tra i parametri per indicare che la
	// funzione prende anche un'immagine
	@PostMapping("/prodotto")
	public String addProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto, BindingResult bindingResult,
			Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {

		// non so se c'è un modo migliore/più corto per prendere il user corrente
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User currentUser = credentials.getUser();
		// assegno il user corrente che sta inserendo un prodotto come "venditore" del
		// prodotto
		prodotto.setUser(currentUser);
		// prodotto.setLuogo(currentUser.getLuogo());

		prodottoValidator.validate(prodotto, bindingResult);

		if (!bindingResult.hasErrors()) {

			String fileName = null;

			// se l'utente ha caricato un'immagine
			if (!multipartFile.isEmpty()) {
				// prendo il nome dell'immagine
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

				// levo gli spazi dal nome dell'immagine (sennò non funziona)
				fileName = fileName.replaceAll("\\s+", "");
				// l'attributo foto è solo il nome della foto, non l'immagine stessa
				prodotto.setFoto(fileName);
			}

			Prodotto prodottoSalvato = prodottoService.save(prodotto); // questo save è diverso dal save del progetto
																		// Catering perché ritorna il prodotto salvato
																		// (se l'avessi fatto così su Catering avrei
																		// risolto tanti problemi)

			if (!multipartFile.isEmpty()) {
				String uploadDir = "prodotto-foto/" + prodottoSalvato.getId();

				// se mettessi lo stesso fileName per tutti (es. "foto"), mi si salverebbe una
				// sola foto e forse è meglio così
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			}

			model.addAttribute("prodotto", prodottoSalvato);
			model.addAttribute("currentUserId", currentUser.getId()); // serve per fare i confronti con th:if

			// "redirect:/pagina" mi permette di non utlizzare una vista come "pagina.html"
			// uso questo in modo da trovarmi subito nella pagina del prodotto CON L'ID
			return "redirect:/prodotto/" + prodottoSalvato.getId();
			// se uso "prodotto.html", dopo l'inserimento di un nuovo prodotto, mi riporta
			// in una pagina "/prodotto" senza l'id e quindi, ricaricando la pagina, dà
			// errore
			// return "prodotto.html";
		}

		model.addAttribute("categorias", this.categoriaService.findAll());
		model.addAttribute("luogos", this.luogoService.findAll());
		return "prodottoForm.html";
	}

//	@PostMapping("/prodotto") FUNZIONE VECCHIA, senza l'upload della foto
//	public String addProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto, BindingResult bindingResult,
//			Model model) {
//
//		prodottoValidator.validate(prodotto, bindingResult);
//
//		if (!bindingResult.hasErrors()) {
//			prodottoService.save(prodotto);
//			model.addAttribute("prodotto", prodotto);
//
//			return "prodotto.html";
//		}
//
//		return "prodottoForm.html";
//	}

	@GetMapping("/prodotto/all")
	public String getProdottos(Model model,@ModelAttribute("keyword") String keyword) {
		
		//keyword è una stringa all'interno della barra di ricerca
		if(keyword!=null) {
			//ho scritto qualcosa nella barra di ricerca allora cerco i prodotti che hanno nel nome la parola keyboard
			model.addAttribute("prodottos", prodottoService.findByKeyword(keyword));
		}
		else {
			//altrimenti ritorno tutti i prodotti
		List<Prodotto> prodottos = prodottoService.findAll();
		model.addAttribute("prodottos", prodottos);
		}
		return "prodottos.html";
	}

	// forse dovrei mettere /prodotto/dettagli/{id} per permettere agli utenti non
	// registrati di vedere i prodotti
	@GetMapping("/prodotto/{id}")
	public String getProdotto(@PathVariable("id") Long id, Model model) {
		Prodotto prodotto = prodottoService.findById(id);
		model.addAttribute("prodotto", prodotto);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User currentUser = credentials.getUser();
		model.addAttribute("currentUserId", currentUser.getId());
		return "prodotto.html";
	}

	@GetMapping("/prodotto/form")
	public String getProdottoForm(Model model) {
		model.addAttribute("prodotto", new Prodotto());
		model.addAttribute("categorias", this.categoriaService.findAll());
		model.addAttribute("luogos", this.luogoService.findAll());
		return "prodottoForm.html";
	}

	@GetMapping("/prodotto/delete/{id}")
	public String deleteProdotto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("prodotto", this.prodottoService.findById(id));

		// pagina di conferma di cancellazione
		return "prodottoDelete.html";
	}

	@GetMapping("/prodotto/delete/confirm/{id}")
	public String confirmDeleteProdotto(@PathVariable("id") Long id,
			/* @RequestParam(value="action", required=true) String action, */ Model model) {
		/*
		 * if(action.equals("Elimina")) { this.prodottoService.deleteById(id); }
		 */
		Prodotto prodotto = this.prodottoService.findById(id);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User currentUser = credentials.getUser();
		// se sei il venditore del prodotto oppure sei un admin, allora puoi effettuare
		// la cancellazione
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE) || prodotto.getUser().equals(currentUser)) {
			this.prodottoService.deleteById(id);

			model.addAttribute("prodottos", prodottoService.findAll());

			return "prodottos.html";
		}

		// sennò, non sei autorizzato a fare la cancellazione
		// lo puoi fare solo inserendo direttamente il sito
		return "unauthorized.html";
	}

	@GetMapping("/prodotto/edit/form/{id}")
	public String editProdottoForm(@PathVariable Long id, Model model) {
		Prodotto prodotto = prodottoService.findById(id);
		model.addAttribute("prodotto", prodotto);
		model.addAttribute("categorias", this.categoriaService.findAll());
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User currentUser = credentials.getUser();
		// se sei il venditore del prodotto oppure sei un admin, allora puoi effettuare
		// la modifica
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE) || prodotto.getUser().equals(currentUser)) {
			return "prodottoEditForm.html";
		}
		// sennò, non sei autorizzato a fare la cancellazione
		// lo puoi fare solo inserendo direttamente il sito
		return "unauthorized.html";
	}

	@PostMapping("/prodotto/edit/{id}")
	public String editProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto, BindingResult bindingResult,
			@PathVariable Long id, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {

		Prodotto vecchioProdotto = this.prodottoService.findById(id);

		if (!vecchioProdotto.equals(prodotto))
			this.prodottoValidator.validate(prodotto, bindingResult);

		if (!bindingResult.hasErrors()) {
			String fileName = null;
			if (!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				fileName = fileName.replaceAll("\\s+", "");
				prodotto.setFoto(fileName); // in questo modo, se non inserisco una nuova foto durante la modifica,
											// allora la vecchia foto viene levata
			}
			prodotto.setId(vecchioProdotto.getId());
			prodotto.setUser(vecchioProdotto.getUser());
			Prodotto prodottoSalvato = this.prodottoService.save(prodotto);
			if (!multipartFile.isEmpty()) {
				String uploadDir = "prodotto-foto/" + prodottoSalvato.getId();
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			}
			model.addAttribute("prodotto", prodottoSalvato);
			return "redirect:/prodotto/" + prodottoSalvato.getId();
		}
		
		model.addAttribute("categorias", this.categoriaService.findAll());

		return "prodottoEditForm.html";
	}
	
	@GetMapping("/prodotto/search")
	public String search(Model model) {
	

		// pagina di conferma di cancellazione
		return "search";
	}
	
	@GetMapping("/prodotti/all")
	public String searchProdotti(Model model,@ModelAttribute("prodotto") Prodotto prodotto,BindingResult bindingResult) {
		
		List<Prodotto> prodottos = prodottoService.findByKeyword(prodotto.getNome());
		model.addAttribute("prodottos", prodottos);

		return "prodottos.html";
	}
}