package br.com.etico.controle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.etico.modelo.beans.Postagem;
import br.com.etico.persistencia.dao.PostagemDAO;

@Named
@SessionScoped
public class PostagemForm implements Serializable {

	private String buscar;

	@Inject
	private PostagemDAO postagemDAO;
	
	public List<Postagem> getPostagems() {

		Session sessao = getPostagemDAO().getSession();
		
		if (buscar == null || buscar.trim().isEmpty()) {
			
			getPostagemDAO().getCriteria().add(Restrictions.eq(Postagem.strTituloPostagem, "")).uniqueResult();
			
			return getPostagemDAO().getRegistros();
		}
		
		
		System.out.println("Busca : " + getBuscar()) ;
		
		String chavesBusca[] = getBuscar().split(" ");
		
		/**
		 * Remove as pre preposições do texto
		 */
		
		Map<Long, String> palavras = new HashMap<>();
		
		removePreposicoes(palavras, chavesBusca);
		
		Set<Long> palavrasSet = palavras.keySet();
		Iterator<Long> iterator = palavrasSet.iterator();
		
		Criteria ct = sessao.createCriteria(Postagem.class);
		
		Disjunction or = Restrictions.disjunction();
		
		while (iterator.hasNext()) {
			
			String palavra = palavras.get(iterator.next());
			System.out.println(palavra);
			or.add(Restrictions.like(Postagem.strTituloPostagem, palavra, MatchMode.ANYWHERE));
			
		}
		
		ct.add(or);
		
		return ct.list();
		
//		try {
//			
//		} finally {
//			sessao.close();
//		}
		 

	}

	
	
	public void buscarPostagem(ActionEvent ev) {}
	
	
	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	private static void removePreposicoes(Map<Long, String> map, String[] chavesBusca) {

		long i = 0;

		Map<String, Object> palavrasUnicas = new HashMap<>();

		for (String palavra : chavesBusca) {

			if (palavra.length() > 4) {

				map.put(++i, palavra);

			}

		}

	}
//
//	public static void main(String[] args) {
//
//		String chavesBusca[] = "busca de empresa filial por fornecedor está divergente".split(" ");
//
//		/**
//		 * Remove as pre preposições do texto
//		 */
//
//		Map<Long, String> palavras = new HashMap<>();
//
//		removePreposicoes(palavras, chavesBusca);
//
//		Set<Long> palavrasSet = palavras.keySet();
//		Iterator<Long> iterator = palavrasSet.iterator();
//
////		Criteria ct = JPAUtil.getSession().createCriteria(Postagem.class);
//
//		Disjunction or = Restrictions.disjunction();
//
//		while (iterator.hasNext()) {
//
//			String palavra = palavras.get(iterator.next());
//			System.out.println(palavra);
//			or.add(Restrictions.like(Postagem.strTituloPostagem, palavra, MatchMode.ANYWHERE));
//
//		}
//
//		ct.add(or);
		

//		return ct.list();
//		//
//		// Map<Long, Lis>
//		//
//		for (Postagem postagem : postagems) {
//			System.out.println(postagem.getTituloPostagem());
//		}

//	}

	public String getBuscar() {
		return buscar;
	}

	public void setBuscar(String buscar) {
		this.buscar = buscar;
	}

	public PostagemDAO getPostagemDAO() {
		return postagemDAO;
	}

	public void setPostagemDAO(PostagemDAO postagemDAO) {
		this.postagemDAO = postagemDAO;
	}

	/**********************/

}
