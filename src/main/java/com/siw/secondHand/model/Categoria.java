package com.siw.secondHand.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	@NotBlank
	private String descrizione;
	
	@OneToMany(mappedBy="categoria")
	private List<Prodotto> prodottos;
	
	@OneToMany(mappedBy="categoria")
	private List<Sottocategoria> sottocategorias;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Prodotto> getProdottos() {
		return prodottos;
	}

	public void setProdottos(List<Prodotto> prodottos) {
		this.prodottos = prodottos;
	}
}
