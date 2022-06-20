package com.siw.secondHand.repository;

import org.springframework.data.repository.CrudRepository;

import com.siw.secondHand.model.Categoria;
import com.siw.secondHand.model.Luogo;
import com.siw.secondHand.model.Prodotto;
import com.siw.secondHand.model.Sottocategoria;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long> {

	public boolean existsByNomeAndDescrizioneAndCategoriaAndSottocategoriaAndLuogoAndFoto(String nome, String descrizione,
			Categoria categoria, Sottocategoria sottocategoria, Luogo luogo, String foto);
}
