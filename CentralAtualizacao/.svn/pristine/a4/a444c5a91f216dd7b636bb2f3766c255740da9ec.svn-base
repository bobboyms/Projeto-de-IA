package br.com.etico.arquitetura.formulario;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import br.com.etico.arquitetura.formulario.util.ELutil;
import br.com.etico.arquitetura.formulario.util.SessaoUsuario;
import br.com.etico.modelo.beans.Usuario;
import br.com.etico.persistencia.dao.UsuarioDAO;

@Named
@SessionScoped
public class Autenticacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static String OK = "ok";
	public static String ERRO = "erro";

	@Inject
	private UsuarioDAO usuarioDao;
	
	private String login;
	private String senha;
	private String msgErro = "";
	private Boolean temMenssagem;
	private Usuario usuario;
	
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
	
	
	public String validaLogin() {

		System.out.println("PUSH BUTTON");	
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		if(getLogin() != null && getSenha() != null){
			
			usuario = getUsuarioDao().getUsuario(login, senha);
			
			if(usuario != null) {
				
				request.getSession().setAttribute(SessaoUsuario.USUARIO, usuario); 
				setMsgErro(null);
				setUsuario(usuario);
				
				System.out.println("LOGOU COM SUCESSO");
				
				return Autenticacao.OK;
				
				
			} else {
				
				setMsgErro("Login ou Senha. Inválidos");
				return Autenticacao.ERRO;
			}
			
		}
		
		setMsgErro("Login ou Senha. Inválidos");
		return Autenticacao.ERRO;
		
	}
	
	public String logout() {
		SessaoUsuario.limpaUsuarioMemoria();
		ELutil.limparTodaSessao();
		return Autenticacao.OK;
	}

	public String getMsgErro() {
		return msgErro;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}

	public Boolean getTemMenssagem() {
		
		temMenssagem = getMsgErro() != null && getMsgErro().trim().length() > 0;
		
		return temMenssagem;
	}

	public void setTemMenssagem(Boolean temMenssagem) {
		this.temMenssagem = temMenssagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

}


//HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//HttpServletResponse rp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//HttpServletRequest rq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//PessoaFacade pessoaService = new PessoaFacadeImpl();
