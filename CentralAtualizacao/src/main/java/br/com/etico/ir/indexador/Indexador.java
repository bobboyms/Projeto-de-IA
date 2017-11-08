package br.com.etico.ir.indexador;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.etico.ir.beans.PostagemTermos;
import br.com.etico.ir.beans.Termos;
import br.com.etico.ir.tokenizacao.Tokenizacao;
import br.com.etico.ir.utils.Termo;
import br.com.etico.modelo.beans.Postagem;
import br.com.etico.persistencia.jpa.JPAUtil;

public class Indexador {

//	public static void main2(String[] args) {
//
//		Session session = null;
//
//		try {
//			//Gera a base de dados para o Weka
//			session = JPAUtil.getSession();
//			List<Postagem> postagems = session.createCriteria(Postagem.class).list();
//			
////			System.out.println("TF,IDF,TF-IDF,CLASSE");
//			
//			for (Postagem postagem : postagems) {
//				
//				Criteria crit = session.createCriteria(PostagemTermos.class);
//				
//				ProjectionList proList = Projections.projectionList();
//				
//				proList.add(Projections.sum(PostagemTermos.strFrequenciaDoTermoTF));
//				proList.add(Projections.sum(PostagemTermos.strFrequenciaDoIndiceReversoTermoIDF));
//				proList.add(Projections.sum(PostagemTermos.strTfIDF));
//				proList.add(Projections.groupProperty(PostagemTermos.strCategoria));
//				
//				crit.add(Restrictions.eq(PostagemTermos.strPostagem, postagem));
//				crit.setProjection(proList);
//				List sumResult = crit.list();
//				
//				Iterator it = sumResult.iterator();
//				
//				while (it.hasNext()) {
//					Object[] row = (Object[]) it.next();
//					
//					System.out.println(row[0] + "," + row[1] + "," + row[2]+ "," + row[3]);
//					
//				}
//			}
//			
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//
//	}

	public static void main(String[] args) {

		Session session = null;

		Tokenizacao tokenizador = new Tokenizacao();
		
		try {
			session = JPAUtil.getSession();
			
			//busca no banco todas as postagens não indexadas
			@SuppressWarnings("unchecked")
			List<Postagem> postagens = session.createCriteria(Postagem.class)
					.add(Restrictions.eq(Postagem.strIndexado, false)).list();

			System.out.println(postagens.size());
			
			if (postagens == null) {
				return;
			}
			
			session.beginTransaction();
			//inicia o processo de index
			for (Postagem postagem : postagens) {
				
				
				List<Termo> termos = tokenizador.tokenizadorTermo(new StringBuilder(postagem.getTituloPostagem()));
				
				// percorre todos os termos de um texto
				for (Termo termo : termos) {
					
					Termos objTerm = (Termos) session.createCriteria(Termos.class)
							.add(Restrictions.eq(Termos.strTermo, termo.getTermo()))
							.uniqueResult();
					
					// verifica se o termo ja existe na tabela de termos
					if (objTerm != null) {
						
						objTerm.setQtdTermoDocumentos(objTerm.getQtdTermoDocumentos()+termo.getQtdTermo());
						objTerm = (Termos)session.merge(objTerm);
						
						//vincula o termo as postagem
						PostagemTermos postagemTermos = new PostagemTermos();
						postagemTermos.setPostagem(postagem);
						postagemTermos.setTermos(objTerm);
						postagemTermos.setQtdTermos(termo.getQtdTermo());
						postagemTermos.setFrequenciaDoTermoTF(termo.getFrequenciaDoTermoTF());
						postagemTermos.setCategoria(postagem.getCategoria());
						session.save(postagemTermos);
						
						continue;
					}

					// se não existir cadastra o termo.
					objTerm = new Termos();
					objTerm.setTermo(termo.getTermo());
					objTerm.setQtdTermoDocumentos(termo.getQtdTermo());
					session.persist(objTerm);
					
					//vincula o termo as postagem
					PostagemTermos postagemTermos = new PostagemTermos();
					postagemTermos.setPostagem(postagem);
					postagemTermos.setTermos(objTerm);
					postagemTermos.setQtdTermos(termo.getQtdTermo());
					postagemTermos.setFrequenciaDoTermoTF(termo.getFrequenciaDoTermoTF());
					postagemTermos.setCategoria(postagem.getCategoria());
					session.persist(postagemTermos);
					
				}
				
				//confirma que foi indexado
				postagem.setIndexado(true);
				
//				session.beginTransaction();
				session.merge(postagem);
				
				
				/**
				 * Executa todo processo. Nenhum erro? Então persiste na base de dados
				 */
			}
			session.getTransaction().commit();				
			
			session.close();
			/****
			 * 
			 * Atualiza o IDF / TF-IDF (peso do termo)
			 * 
			 *****/
			
			session = JPAUtil.getSession();
			//
			double qtdDocumentos = (long) session.createCriteria(Postagem.class)
					.setProjection(Projections.rowCount()).uniqueResult();
			
			List<Termos> termos = session.createCriteria(Termos.class).list();

			session.beginTransaction();
			
			for (Termos termo : termos) {

				List<PostagemTermos> postagemTermos = session
						.createCriteria(PostagemTermos.class)
						.add(Restrictions.eq(PostagemTermos.strTermos, termo))
						.list();

				for (PostagemTermos postagemTermo : postagemTermos) {
					postagemTermo.setFrequenciaDoIndiceReversoTermoIDF(Math.log(qtdDocumentos / postagemTermos.size()));
					postagemTermo.setTfIDF(postagemTermo.getFrequenciaDoTermoTF() * postagemTermo.getFrequenciaDoIndiceReversoTermoIDF());
					session.update(postagemTermo);
				}

			}
			
			session.flush();
			session.clear();
			session.getTransaction().commit();

			

		} catch (Exception e) {

			e.printStackTrace(); // Travo? trava! Reinicia esse banco.
			
			session.getTransaction().rollback(); // Testa.
		} finally {

			if (session != null) {
				session.close();
			}
		}

	}
}

