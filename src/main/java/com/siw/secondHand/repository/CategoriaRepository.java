package com.siw.secondHand.repository;

import org.springframework.data.repository.CrudRepository;

import com.siw.secondHand.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

	public boolean existsByNomeAndDescrizione(String nome, String descrizione);
}
