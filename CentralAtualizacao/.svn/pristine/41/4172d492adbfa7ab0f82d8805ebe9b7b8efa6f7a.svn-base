package br.com.etico.ir.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_termos")
public class Termos extends EntidadePersistence implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String strTermo = "termo";
	
	public static final String strPostagem = "postagem";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="termo", nullable=false)
	private String termo;
	
	@Column(name="qtd_termo_documentos", nullable=false)
	private Long qtdTermoDocumentos;
	
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

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public Long getQtdTermoDocumentos() {
		return qtdTermoDocumentos;
	}

	public void setQtdTermoDocumentos(Long qtdTermoDocumentos) {
		this.qtdTermoDocumentos = qtdTermoDocumentos;
	}


//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_postagem")
//	private Postagem postagem;

}
