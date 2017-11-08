package br.com.etico.modelo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_anexo_mensagem_Chamado")
public class AnexoMensagemDoChamado extends EntidadePersistence {
	
	private static final long serialVersionUID = 1L;

	public static final String strMensagemDoChamado = "mensagemDoChamado";
	public static final String strNomeArquivo = "nomeArquivo";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mensagem_chamado")
	private MensagemDoChamado mensagemDoChamado;
	
	@Column(name="nome_arquivo")
	private String nomeArquivo;
	
	@Column(name="content_type")
	private String contentType;
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setValoresPadroes() {
		// TODO Auto-generated method stub
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public MensagemDoChamado getMensagemDoChamado() {
		return mensagemDoChamado;
	}

	public void setMensagemDoChamado(MensagemDoChamado mensagemDoChamado) {
		this.mensagemDoChamado = mensagemDoChamado;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
