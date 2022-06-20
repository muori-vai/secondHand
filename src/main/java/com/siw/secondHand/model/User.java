package com.siw.secondHand.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users") // cambiamo nome perché in postgres user è una parola riservata
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	
	@OneToMany(mappedBy="user", cascade={CascadeType.ALL})
	private List<Prodotto> prodottos;
	
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
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Prodotto> getProdottos() {
		return prodottos;
	}

	public void setProdottos(List<Prodotto> prodottos) {
		this.prodottos = prodottos;
	}

}
