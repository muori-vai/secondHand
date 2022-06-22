package com.siw.secondHand.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siw.secondHand.model.Luogo;
import com.siw.secondHand.repository.LuogoRepository;

@Service
public class LuogoService {

	@Autowired
	private LuogoRepository luogoRepository;

	@Transactional
	public Luogo save(Luogo luogo) {
		return luogoRepository.save(luogo);
	}
	
	@Transactional
	public void delete(Luogo luogo) {
		luogoRepository.delete(luogo);
	}
	
	@Transactional
	public void deleteById(Long id) {
		luogoRepository.deleteById(id);
	}

	public Luogo findById(Long id) {
		return luogoRepository.findById(id).get();
	}
	
	public List<Luogo> findAll() {
		List<Luogo> luogos = new ArrayList<Luogo>();
		
		for(Luogo l: luogoRepository.findAll()) {
			luogos.add(l);
		}
		
		return luogos;
	}
	
	public boolean alreadyExists(Luogo luogo) {
		return luogoRepository.existsByNome(luogo.getNome());
	}
}
