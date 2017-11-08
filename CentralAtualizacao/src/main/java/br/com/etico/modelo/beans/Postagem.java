package br.com.etico.modelo.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_postagem")
public class Postagem extends EntidadePersistence implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String strTituloPostagem = "tituloPostagem";
	public static final String strIndexado = "indexado";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="titulo_postagem", nullable=false)
	private String tituloPostagem;
	
	@Column(name="indexado")
	private Boolean indexado = false;
	
	@Column(name="categoria")
	private String categoria;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setValoresPadroes() {
	}

	public String getTituloPostagem() {
		return tituloPostagem;
	}

	public void setTituloPostagem(String tituloPostagem) {
		this.tituloPostagem = tituloPostagem;
	}

	public Boolean getIndexado() {
		return indexado;
	}

	public void setIndexado(Boolean indexado) {
		this.indexado = indexado;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}