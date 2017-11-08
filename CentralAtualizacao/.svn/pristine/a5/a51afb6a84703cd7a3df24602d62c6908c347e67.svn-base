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
import javax.persistence.Transient;

import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_usuario")
public class Usuario extends EntidadePersistence {
	
	private static final long serialVersionUID = 1L;

	public static final String strNomeUsuario = "nomeUsuario";
	public static final String strLogin = "login";
	public static final String strSenha = "senha";
	public static final String strSetor = "setor";
	public static final String strEmpresa = "empresa";
	public static final String strEmail = "email";
	public static final String strPerfil = "perfil";
	public static final String strPermiteEmpresa = "permiteEmpresa";
	public static final String strPermiteUsuario = "permiteUsuario";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome_usuario", nullable=false)
	private String nomeUsuario;
	
	@Column(name="login", nullable=false)
	private String login;
	
	@Column(name="senha", nullable=false)
	private String senha;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	//TODO: PERMISOES, MELHORAR DEPOIS
	
	@Column(name="permite_empresa")
	private boolean permiteEmpresa = false;
	
	@Column(name="permite_usuario")
	private boolean permiteUsuario = false;
	
	@Transient
	private Chamado chamadoExterno;
	
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
		
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Chamado getChamadoExterno() {
		return chamadoExterno;
	}

	public void setChamadoExterno(Chamado chamadoExterno) {
		this.chamadoExterno = chamadoExterno;
	}

	public boolean isPermiteEmpresa() {
		return permiteEmpresa;
	}

	public void setPermiteEmpresa(boolean permiteEmpresa) {
		this.permiteEmpresa = permiteEmpresa;
	}

	public boolean isPermiteUsuario() {
		return permiteUsuario;
	}

	public void setPermiteUsuario(boolean permiteUsuario) {
		this.permiteUsuario = permiteUsuario;
	}
	
}
