package com.siw.secondHand.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Prodotto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;
	@NotBlank
	private String descrizione;

	// per ora considero Categoria, forse più avanti lo levo in quanto ridondante
	@NotNull
	@ManyToOne // (cascade = { CascadeType.ALL })
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "sottocategoria_id")
	private Sottocategoria sottocategoria;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
//	@NotNull
//	@ManyToOne
//	@JoinColumn(name = "luogo_id")
//	private Luogo luogo;

	@Column(nullable = true, length = 64)
	private String foto;

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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFotoImagePath() {
		if (foto == null || id == null)
			return null;
		// si potrebbe fare un return "/generic-foto/prodotto"

		return "/images/prodotto-foto/" + id + "/" + foto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Sottocategoria getSottocategoria() {
		return sottocategoria;
	}

	public void setSottocategoria(Sottocategoria sottocategoria) {
		this.sottocategoria = sottocategoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, descrizione, foto, nome, sottocategoria);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prodotto other = (Prodotto) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(descrizione, other.descrizione)
				&& Objects.equals(foto, other.foto) && Objects.equals(nome, other.nome)
				&& Objects.equals(sottocategoria, other.sottocategoria);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public Luogo getLuogo() {
//		return luogo;
//	}
//
//	public void setLuogo(Luogo luogo) {
//		this.luogo = luogo;
//	}
}
