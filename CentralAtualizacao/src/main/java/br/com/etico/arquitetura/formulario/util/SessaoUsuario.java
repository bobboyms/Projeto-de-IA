package br.com.etico.arquitetura.formulario.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.etico.modelo.beans.Usuario;

public class SessaoUsuario {
	
	public static final String USUARIO = "usuario";
	
	public static final String MODULO = "modulo";
	
//	public static synchronized final Empresa getEmpresaConectada() {
//		
//		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		
//		return (Empresa)request.getSession().getAttribute(SessaoUsuario.EMPRESA);
//		
//	}
	
	public static synchronized final void limpaUsuarioMemoria() {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().setAttribute(SessaoUsuario.USUARIO, null);
		
	}
	
	public static synchronized final Usuario getUsuarioConectado() {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return (Usuario) request.getSession().getAttribute(SessaoUsuario.USUARIO);
		
	}
	
//	public static synchronized final Modulo getModuloAtual() {
//		
//		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		
//		return (Modulo)request.getSession().getAttribute(SessaoUsuario.MODULO);
//		
//	}
	
//	public static synchronized final String getSchema() {
//		
//		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		
//		return (String) request.getSession().getAttribute(SessaoUsuario.SCHEMA);
//		
//	}
	
	public static final void limparSessao() {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		request.getSession().setAttribute(SessaoUsuario.MODULO,null);
		
		request.getSession().setAttribute(SessaoUsuario.USUARIO,null);
		
		request.getSession().setAttribute(SessaoUsuario.USUARIO,null);
		
	}

}
