package com.siw.secondHand.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siw.secondHand.model.Prodotto;
import com.siw.secondHand.repository.ProdottoRepository;

@Service
public class ProdottoService {

	@Autowired
	private ProdottoRepository prodottoRepository;

	@Transactional
	public Prodotto save(Prodotto prodotto) {
		return prodottoRepository.save(prodotto); //dopo aver salvato, ritorna il prodotto salvato
	}
	
	@Transactional
	public void delete(Prodotto prodotto) {
		prodottoRepository.delete(prodotto);
	}
	
	@Transactional
	public void deleteById(Long id) {
		prodottoRepository.deleteById(id);
	}

	public Prodotto findById(Long id) {
		return prodottoRepository.findById(id).get();
	}
	
	public List<Prodotto> findAll() {
		List<Prodotto> prodottos = new ArrayList<Prodotto>();
		
		for(Prodotto p: prodottoRepository.findAll()) {
			prodottos.add(p);
		}
		
		return prodottos;
	}
	
	public boolean alreadyExists(Prodotto prodotto) {
		return prodottoRepository.existsByNomeAndDescrizione(prodotto.getNome(), prodotto.getDescrizione());
	}
	
	public boolean illegalSottocategoria(Prodotto prodotto) {
		return !prodotto.getCategoria().equals(prodotto.getSottocategoria().getCategoria());
	}
}
