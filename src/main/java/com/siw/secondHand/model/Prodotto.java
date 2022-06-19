package com.siw.secondHand.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Prodotto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;
	@NotBlank
	private String descrizione;

	@ManyToOne//(cascade = { CascadeType.ALL })
	@JoinColumn(name="categoria_id", nullable=false)
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name="sottocategoria_id", nullable=true)
	private Sottocategoria sottocategoria;
	
	@ManyToOne
	@JoinColumn(name="luogo_id", nullable=false)
	private Luogo luogo;

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
		//si potrebbe fare un return "/generic-foto/prodotto" 
		
		return "/prodotto-foto/" + id + "/" + foto;
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

	public Luogo getLuogo() {
		return luogo;
	}

	public void setLuogo(Luogo luogo) {
		this.luogo = luogo;
	}
}
