package com.siw.secondHand.repository;

import org.springframework.data.repository.CrudRepository;

import com.siw.secondHand.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long> {

	public boolean existsByNomeAndDescrizione(String nome, String descrizione);
}
