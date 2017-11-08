package br.com.etico.ir.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.etico.modelo.beans.Postagem;
import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_postagem_termos")
public class PostagemTermos extends EntidadePersistence implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String strPostagem = "postagem";
	public static final String strTermos = "termos";
	public static final String strQtdTermos = "qtdTermos";
	public static final String strFrequenciaDoTermoTF = "frequenciaDoTermoTF";
	public static final String strTfIDF = "tfIDF";
	public static final String strFrequenciaDoIndiceReversoTermoIDF = "frequenciaDoIndiceReversoTermoIDF";
	public static final String strCategoria = "categoria";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_postagem")
	private Postagem postagem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_termos")
	private Termos termos;

	@Column(name="qtd_termos", nullable=false)
	private Long qtdTermos;
	
	@Column(name="frequencia_do_termo_tf", nullable=false)
	private Double frequenciaDoTermoTF;
	
	@Column(name="frequencia_do_indice_reverso_termo_idf", nullable=false)
	private Double frequenciaDoIndiceReversoTermoIDF = (double) 0;
	
	@Column(name="tf_idf", nullable=false)
	private Double tfIDF = (double) 0;
	
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

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	public Termos getTermos() {
		return termos;
	}

	public void setTermos(Termos termos) {
		this.termos = termos;
	}

	public Long getQtdTermos() {
		return qtdTermos;
	}

	public void setQtdTermos(Long qtdTermos) {
		this.qtdTermos = qtdTermos;
	}

	public Double getFrequenciaDoTermoTF() {
		return frequenciaDoTermoTF;
	}

	public void setFrequenciaDoTermoTF(Double frequenciaDoTermoTF) {
		this.frequenciaDoTermoTF = frequenciaDoTermoTF;
	}

	public Double getFrequenciaDoIndiceReversoTermoIDF() {
		return frequenciaDoIndiceReversoTermoIDF;
	}

	public void setFrequenciaDoIndiceReversoTermoIDF(
			Double frequenciaDoIndiceReversoTermoIDF) {
		this.frequenciaDoIndiceReversoTermoIDF = frequenciaDoIndiceReversoTermoIDF;
	}

	public Double getTfIDF() {
		return tfIDF;
	}

	public void setTfIDF(Double tfIDF) {
		this.tfIDF = tfIDF;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
