package br.com.etico.controle;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etico.modelo.beans.Postagem;
import br.com.etico.persistencia.dao.PostagemDAO;

@Named
@RequestScoped
public class PostagemSelecionadaForm {

	@Inject
	private PostagemDAO postagemDAO;

	public Postagem getPostagem() {

		String idPost = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idpost");

		System.out.println("ID POSTAGEM : " + idPost);

		
		return (Postagem) getPostagemDAO().getRegistro(new Long(idPost));

	}

	public PostagemDAO getPostagemDAO() {
		return postagemDAO;
	}

	public void setPostagemDAO(PostagemDAO postagemDAO) {
		this.postagemDAO = postagemDAO;
	}

}
