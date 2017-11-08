package br.com.etico.controle.ir;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.etico.ir.beans.PostagemTermos;
import br.com.etico.ir.indexador.Indexador;
import br.com.etico.modelo.beans.Postagem;
import br.com.etico.persistencia.dao.PostagemDAO;
import br.com.etico.persistencia.dao.PostagemTermoDAO;
import br.com.etico.persistencia.dao.TermoDAO;

@Named
@SessionScoped
public class TesteIrForm implements Serializable {

	private String texto;
	
	@Inject
	private PostagemDAO postagemDAO;
	
	@Inject
	private PostagemTermoDAO postagemTermoDAO;
	
	@Inject
	private TermoDAO termoDAO;
	
	public List<PostagemTermos> getPostagemTermos() {
		
		return getPostagemTermoDAO().getRegistros();
		
	}
	
	public void cadastrar(ActionEvent ev) {
		
		Postagem postagem = new Postagem();
		postagem.setTituloPostagem(getTexto());
		
		getPostagemDAO().inserir(postagem);
		
		//Executa o indexador
		Indexador.main(new String[]{""});
		
		setTexto("");
		
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public PostagemTermoDAO getPostagemTermoDAO() {
		return postagemTermoDAO;
	}

	public void setPostagemTermoDAO(PostagemTermoDAO postagemTermoDAO) {
		this.postagemTermoDAO = postagemTermoDAO;
	}

	public PostagemDAO getPostagemDAO() {
		return postagemDAO;
	}

	public void setPostagemDAO(PostagemDAO postagemDAO) {
		this.postagemDAO = postagemDAO;
	}


	public TermoDAO getTermoDAO() {
		return termoDAO;
	}


	public void setTermoDAO(TermoDAO termoDAO) {
		this.termoDAO = termoDAO;
	}
	
	public static double tf(List<String> doc, String term) {
	    double result = 0;
	    for (String word : doc) {
	       if (term.equalsIgnoreCase(word))
	              result++;
	       }
	    return result / doc.size();
	}
	
	public static void main(String[] args) {
		
		System.out.println(tf(Arrays.asList("Lorem", "ipsum", "dolor", "ipsum", "sit", "ipsum"),"ipsum"));
		
	}

	/**********************/

}
