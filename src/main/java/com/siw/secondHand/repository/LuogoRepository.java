package com.siw.secondHand.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.siw.secondHand.model.Luogo;

public interface LuogoRepository extends CrudRepository<Luogo, Long> {

	public boolean existsByNome(String nome);
	
	public List<Luogo> findByOrderByNome();
}
