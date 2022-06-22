package com.siw.secondHand.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siw.secondHand.model.Categoria;
import com.siw.secondHand.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Transactional
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria); // dopo aver salvato, ritorna la categoria salvata
	}

	@Transactional
	public void delete(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}

	@Transactional
	public void deleteById(Long id) {
		categoriaRepository.deleteById(id);
	}

	public Categoria findById(Long id) {
		return categoriaRepository.findById(id).get();
	}

	public List<Categoria> findAll() {
		List<Categoria> categorias = new ArrayList<Categoria>();

		for (Categoria c : categoriaRepository.findAll()) {
			categorias.add(c);
		}

		return categorias;
	}

	public boolean alreadyExists(Categoria categoria) {
		return categoriaRepository.existsByNomeAndDescrizione(categoria.getNome(), categoria.getDescrizione());
	}
}
