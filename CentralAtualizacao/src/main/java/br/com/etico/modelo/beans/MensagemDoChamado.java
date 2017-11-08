package br.com.etico.modelo.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.etico.arquitetura.formulario.util.SessaoUsuario;
import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_mensagem_chamado")
public class MensagemDoChamado extends EntidadePersistence {
	
	private static final long serialVersionUID = 1L;

	public static final String strChamado = "chamado";
	public static final String strMensagem = "mensagem";
	public static final String strPossuiAnexo = "possuiAnexo";
	public static final String strRespondido = "respondido";
	public static final String strUsuarioQueRespondeu = "usuarioQueRespondeu";
	public static final String strDthResposta = "dthResposta";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_chamado")
	private Chamado chamado;
	
	@Column(name="mensagem")
	private String mensagem;
	
	@Column(name="possui_anexo", nullable=false)
	private Boolean possuiAnexo = false;
	
	@Column(name="respondido", nullable=false)
	private Boolean respondido = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuarioQueRespondeu;
	
	@Column(name="dth_resposta", nullable=false)
	private Date dthResposta;
	
	
	//TODO FAZER 
	
//	@OneToMany(fetch=FetchType.EAGER )
//	@JoinColumn(name="id_mensagem_chamado")
//	private List<AnexoMensagemDoChamado> anexoMensagemDoChamados;
	
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

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Boolean getPossuiAnexo() {
		return possuiAnexo;
	}

	public void setPossuiAnexo(Boolean possuiAnexo) {
		this.possuiAnexo = possuiAnexo;
	}

	public Boolean getRespondido() {
		return respondido;
	}
	
	@Transient
	public boolean isRespondidoSuporte() {
		
		if (getUsuarioQueRespondeu().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_SUPORTE)) {
			return true;
		}
		
		return false;
		
	}
	
	@Transient
	public boolean isMesmoUsuarioCriou() {
		
		if (getUsuarioQueRespondeu().equals(SessaoUsuario.getUsuarioConectado())) return true;
		
		return false;
	}

	public void setRespondido(Boolean respondido) {
		this.respondido = respondido;
	}

	public Usuario getUsuarioQueRespondeu() {
		return usuarioQueRespondeu;
	}

	public void setUsuarioQueRespondeu(Usuario usuarioQueRespondeu) {
		this.usuarioQueRespondeu = usuarioQueRespondeu;
	}

	@Transient
	public String getDthRespostaFormatado() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return format.format(getDthResposta());
	}

	public Date getDthResposta() {
		return dthResposta;
	}

	public void setDthResposta(Date dthResposta) {
		this.dthResposta = dthResposta;
	}

//	public List<AnexoMensagemDoChamado> getAnexoMensagemDoChamados() {
//		return anexoMensagemDoChamados;
//	}
//
//	public void setAnexoMensagemDoChamados(List<AnexoMensagemDoChamado> anexoMensagemDoChamados) {
//		this.anexoMensagemDoChamados = anexoMensagemDoChamados;
//	}
//	
//	@Transient
//	public boolean isTemAnexo() {
//		
//		if (getAnexoMensagemDoChamados() == null || getAnexoMensagemDoChamados().size() <= 0) {
//			return false;
//		}
//		
//		return true;
//		
//	}

}
