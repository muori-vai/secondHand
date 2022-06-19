package com.siw.secondHand.repository;

import org.springframework.data.repository.CrudRepository;

import com.siw.secondHand.model.Luogo;

public interface LuogoRepository extends CrudRepository<Luogo, Long> {

	public boolean existsByNome(String nome);
}
