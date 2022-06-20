package com.siw.secondHand.repository;

import org.springframework.data.repository.CrudRepository;

import com.siw.secondHand.model.Categoria;
import com.siw.secondHand.model.Sottocategoria;

public interface SottocategoriaRepository extends CrudRepository<Sottocategoria, Long> {

	public boolean existsByNomeAndDescrizioneAndCategoria(String nome, String descrizione, Categoria categoria);
}
