package com.siw.secondHand.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siw.secondHand.model.Sottocategoria;
import com.siw.secondHand.repository.SottocategoriaRepository;

@Service
public class SottocategoriaService {

	@Autowired
	private SottocategoriaRepository sottocategoriaRepository;

	@Transactional
	public Sottocategoria save(Sottocategoria sottocategoria) {
		return sottocategoriaRepository.save(sottocategoria);
	}

	@Transactional
	public void delete(Sottocategoria sottocategoria) {
		sottocategoriaRepository.delete(sottocategoria);
	}

	@Transactional
	public void deleteById(Long id) {
		sottocategoriaRepository.deleteById(id);
	}

	public Sottocategoria findById(Long id) {
		return sottocategoriaRepository.findById(id).get();
	}

	public List<Sottocategoria> findAll() {
		List<Sottocategoria> sottocategorias = new ArrayList<Sottocategoria>();

		for (Sottocategoria s : sottocategoriaRepository.findAll()) {
			sottocategorias.add(s);
		}

		return sottocategorias;
	}

	public boolean alreadyExists(Sottocategoria sottocategoria) {
		return sottocategoriaRepository.existsByNomeAndDescrizioneAndCategoria(sottocategoria.getNome(),
				sottocategoria.getDescrizione(), sottocategoria.getCategoria());
	}
}
