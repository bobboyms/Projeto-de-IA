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

import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_chamado")
public class Chamado extends EntidadePersistence {
	
	private static final long serialVersionUID = 1L;

	public static final String strTituloChamado = "tituloChamado";
	public static final String strIndStatusChamado = "indStatusChamado";
	public static final String strDataAbertura = "dataAbertura";
	public static final String strDataUltimaAlteracao = "dataUltimaAlteracao";
	public static final String strUsuarioQueAbriu = "usuarioQueAbriu";
	public static final String strFechado = "fechado";
	public static final String strUsuarioQueFechou = "usuarioQueFechou";
	public static final String strSetorAtual = "setorAtual";
	public static final String strEmpresa = "empresa";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="titulo_chamado", nullable=false)
	private String tituloChamado;
	
	@Column(name="data_abertura", nullable=false)
	private Date dataAbertura;
	
	@Column(name="data_ultima_alteracao", nullable=true)
	private Date dataUltimaAlteracao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_abriu")
	private Usuario usuarioQueAbriu;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuarioQueFechou;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_setor")
	private Perfil setorAtual;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	/**
	 * 
	 *  PC = PENDENTE CLIENTE
	 *  PE = PENDENTE EMPRESA
	 *  PT = PENDENTE T.I
	 * 
	 */
	
	@Column(name="ind_status_chamado", nullable=false)
	private String indStatusRespostaChamado;
	
	
//	itemsStatus.add(new SelectItem("AB", "ABERTO"));
//	itemsStatus.add(new SelectItem("AN", "ANDAMENTO"));
//	itemsStatus.add(new SelectItem("FE", "FECHADO"));
	@Column(name="ind_status", nullable=false)
	private String indStatus;
	
	@Column(name="ind_avaliacao")
	private String indAvaliacao;
	
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
		setDataAbertura(new Date());
		setIndStatus("AB");
	}

	public String getTituloChamado() {
		return tituloChamado;
	}

	public void setTituloChamado(String tituloChamado) {
		this.tituloChamado = tituloChamado;
	}

	
	public String getIndStatusRespostaChamado() {
		return indStatusRespostaChamado;
	}

	public void setIndStatusRespostaChamado(String indStatusRespostaChamado) {
		this.indStatusRespostaChamado = indStatusRespostaChamado;
	}
	
	
	/**
	 * 
	 *  PC = PENDENTE CLIENTE
	 *  PE = PENDENTE EMPRESA
	 *  PT = PENDENTE T.I
	 * 
	 */
	
	@Transient
	public String getIndStatusRespostaChamadoFormatado() {
		
		if (getIndStatusRespostaChamado() == null) {
			return "CRIANDO CHAMADO";
		}
		
		if (getIndStatusRespostaChamado().equals("PT")) {
			return "PENDENTE T.I ETICO";
		}
		if (getIndStatusRespostaChamado().equals("PC")) {
			return "PENDENTE CLIENTE";
		}
		if (getIndStatusRespostaChamado().equals("PE")) {
			return "PENDENTE ETICO";
		}
		
		return "CRIANDO CHAMADO";
		
	}


	@Transient
	public String getIndStatusFormatado() {
		
		if (getIndStatus() == null) {
			return "ABERTO";
		}
		
		if (getIndStatus().equals("AB")) {
			return "ABERTO";
		}
		if (getIndStatus().equals("AN")) {
			return "ANDAMENTO";
		}
		if (getIndStatus().equals("FE")) {
			return "FECHADO";
		}
		
		return "ABERTO";
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}
	
	@Transient
	public String getDataUltimaAlteracaoFormatado() {
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return format.format(getDataUltimaAlteracao());
		
//		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	@Transient
	public String getDataAberturaFormatado() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return format.format(getDataAbertura());
	}
	
	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Usuario getUsuarioQueAbriu() {
		return usuarioQueAbriu;
	}

	public void setUsuarioQueAbriu(Usuario usuarioQueAbriu) {
		this.usuarioQueAbriu = usuarioQueAbriu;
	}

	public Usuario getUsuarioQueFechou() {
		return usuarioQueFechou;
	}

	public void setUsuarioQueFechou(Usuario usuarioQueFechou) {
		this.usuarioQueFechou = usuarioQueFechou;
	}

	public Perfil getSetorAtual() {
		return setorAtual;
	}

	public void setSetorAtual(Perfil setorAtual) {
		this.setorAtual = setorAtual;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getIndStatus() {
		return indStatus;
	}

	public void setIndStatus(String indStatus) {
		this.indStatus = indStatus;
	}

	public String getIndAvaliacao() {
		return indAvaliacao;
	}

	public void setIndAvaliacao(String indAvaliacao) {
		this.indAvaliacao = indAvaliacao;
	}

	
	
}
