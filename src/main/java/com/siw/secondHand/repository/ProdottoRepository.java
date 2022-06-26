package com.siw.secondHand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.siw.secondHand.model.Categoria;
import com.siw.secondHand.model.Prodotto;
import com.siw.secondHand.model.Sottocategoria;
import com.siw.secondHand.model.User;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long> {

	public boolean existsByNomeAndDescrizioneAndFotoAndUserAndCategoriaAndSottocategoria(String nome,
			String descrizione, String foto, User user, Categoria categoria, Sottocategoria sottocategoria);

	public List<Prodotto> findAllByOrderByIdDesc(); // per avere la lista al contrario (in modo da avere gli ultimi
													// inseriti come i primi)

	@Query(value="select * from prodotto p where p.nome like (%:keyword%) order by p.id desc", nativeQuery=true)
	List<Prodotto> findByKeyword(@Param("keyword") String keyword);
}
