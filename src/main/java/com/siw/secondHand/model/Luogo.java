package com.siw.secondHand.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Luogo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;

	@OneToMany(mappedBy = "luogo"/* , cascade={CascadeType.REMOVE} */)
	private List<User> users;

	// ridondante, forse dopo possiamo anche ometterlo
//	@OneToMany(mappedBy = "luogo"/* , cascade={CascadeType.REMOVE} */)
//	private List<Prodotto> prodottos;

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Luogo other = (Luogo) obj;
		return Objects.equals(nome, other.nome);
	}

//	public List<Prodotto> getProdottos() {
//		return prodottos;
//	}
//
//	public void setProdottos(List<Prodotto> prodottos) {
//		this.prodottos = prodottos;
//	}
}
