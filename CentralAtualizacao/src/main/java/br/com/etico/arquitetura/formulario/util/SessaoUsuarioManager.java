package br.com.etico.arquitetura.formulario.util;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.etico.modelo.beans.Usuario;
import br.com.etico.persistencia.jpa.JPAUtil;

@Named("sessaoUsuarioManager")
@SessionScoped
public class SessaoUsuarioManager implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioMemoria;

	
	public Usuario getUsuarioMemoria() {

		if (usuarioMemoria == null) {

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext()
					.getRequest();

			Usuario usuario = (Usuario) request.getSession().getAttribute(SessaoUsuario.USUARIO);

			Criteria ct = JPAUtil.getSession().createCriteria(Usuario.class);
			ct.add(Restrictions.eq(Usuario.strId, usuario.getId()));

			usuarioMemoria = (Usuario) ct.uniqueResult();
			usuarioMemoria.setChamadoExterno(usuario.getChamadoExterno());

		}

		return usuarioMemoria;
	}


}
