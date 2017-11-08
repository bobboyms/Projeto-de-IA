package br.com.etico.persistencia.dao;

import org.hibernate.criterion.Restrictions;

import br.com.etico.modelo.beans.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario>  {

	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super(Usuario.class);
	}
	
	public Usuario getUsuario(String login, String senha) {
		
		System.out.println(getCriteria() == null);
		
		return (Usuario) getCriteria().add(Restrictions.eq(Usuario.strLogin, login))
					 				  .add(Restrictions.eq(Usuario.strSenha, senha))
					 				  .uniqueResult();
		
	}
	

}
