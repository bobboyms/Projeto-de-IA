package br.com.etico.ir.utils;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.etico.ir.beans.PostagemTermos;
import br.com.etico.ir.beans.Termos;
import br.com.etico.modelo.beans.Postagem;
import br.com.etico.persistencia.jpa.JPAUtil;

public class Termo {

	private long qtdTermo = 1;

	private String termo;

	private double frequenciaDoTermoTF = 0;

	public Termo(String termo) {
		setTermo(termo);
	}

	@Override
	public boolean equals(Object arg0) {

		String termo = ((Termo) arg0).getTermo();

		if (getTermo().equals(termo)) {
			return true;
		}

		return false;

	}

	public long getQtdTermo() {
		return qtdTermo;
	}

	public void inclementaQtd() {
		qtdTermo++;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public double getFrequenciaDoTermoTF() {
		return frequenciaDoTermoTF;
	}

	public void setFrequenciaDoTermoTF(double frequenciaDoTermoTF) {
		this.frequenciaDoTermoTF = frequenciaDoTermoTF;
	}

	public double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        
        return result / doc.size();
    }

    /**
     * @param docs list of list of strings represents the dataset
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        
        System.out.println("size : " + docs.size() + " " + n);
        
        return Math.log(docs.size() / n);
    }

    /**
     * @param doc  a text document
     * @param docs all documents
     * @param term term
     * @return the TF-IDF of term
     */
    public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        
    	System.out.println("IDF : " + idf(docs, term));
    	
    	return tf(doc, term) * idf(docs, term);

    }

    public static void main(String[] args) {

    	Session session = JPAUtil.getSession();
    	
    	Criteria ct = session.createCriteria(Termos.class)
    			.add(Restrictions.in(Termos.strTermo, new String[]{"ipsum","pro","has"} ));
//    	
//    	List<Termos> termos = ct.list();
//    	
//    	for (Termos termo : termos) {
//    		
//    		System.out.println(termo.getId() + " " + termo.getTermo());
//    		
//    	}
    	
    	
    	ct = session.createCriteria(PostagemTermos.class);
    	
    	ct.addOrder(Order.asc(PostagemTermos.strPostagem));
    	
    	ct.createAlias(PostagemTermos.strTermos, "termos")
    		.add(Restrictions.in("termos." + Termos.strTermo, new String[]{"ipsum","pro","has", "lorem"}));
    	
    	List<PostagemTermos> postagemTermos = ct.list();
    	
    	System.out.println(Double.parseDouble("0.1831020481113516") + Double.parseDouble("0.2027325540540822"));
    	
//    	long id = 0;
//    	int i = 0;
//    	double soma = 0;
//    	for (PostagemTermos postagemTermo : postagemTermos) {
//    		
//    		System.out.println(postagemTermo.getPostagem().getId() + " " + postagemTermo.getTermos().getTermo()+ " " + postagemTermo.getTfIDF());
//    		
//    		if (i == 0) {
//    			id = postagemTermo.getId();
//    			i = 2;
//    		}
//    		
//    		
//    		if (id == postagemTermo.getId()) {
//    			soma = + postagemTermo.getTfIDF();
//    		} else {
//    			
//    			System.out.println("SOMA : " + soma + " " + postagemTermo.getPostagem().getId());
//    			
//    			soma = 0;
//    			soma = + postagemTermo.getTfIDF();
//    			i = 0;
//    		}
//    		
//    		
//    		
//    	}
    	
//    	
    	
    	
    	
//        List<String> doc1 = Arrays.asList("Lorem", "ipsum", "dolor", "ipsum", "sit", "ipsum");
//        List<String> doc2 = Arrays.asList("Vituperata", "incorrupte", "at", "ipsum", "pro", "quo");
//        List<String> doc3 = Arrays.asList("Has", "persius", "disputationi", "id", "simul");
//        List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);
//        
//        Termo calculator = new Termo("");
//       
//        System.out.println(calculator.tf(doc1, "ipsum"));
//        
//        double tfidf = calculator.tfIdf(doc1, documents, "ipsum");
//        System.out.println("TF-IDF (ipsum) = " + tfidf);
//
//    	Session session = null;
//    	
//	try {
//			
//			session = JPAUtil.getSession();
//			//
//			double qtdDocumentos = (long) session.createCriteria(Postagem.class)
//					.setProjection(Projections.rowCount()).uniqueResult();
//
//			List<Termos> termos = session.createCriteria(Termos.class).list();
//
//			session.beginTransaction();
//			
//			for (Termos termo : termos) {
//
//				List<PostagemTermos> postagemTermos = session
//						.createCriteria(PostagemTermos.class)
//						.add(Restrictions.eq(PostagemTermos.strTermos, termo))
//						.list();
//
//				for (PostagemTermos postagemTermo : postagemTermos) {
//					postagemTermo.setFrequenciaDoIndiceReversoTermoIDF(Math.log(qtdDocumentos / postagemTermos.size()));
//					postagemTermo.setTfIDF(postagemTermo.getFrequenciaDoTermoTF() * postagemTermo.getFrequenciaDoIndiceReversoTermoIDF());
//					session.update(postagemTermo);
//				}
//
//			}
//			
//			session.getTransaction().commit();
//
//			session.flush();
//			session.clear();
//			
//		} catch (Exception e) {
//			session.getTransaction().rollback();
//
//			e.printStackTrace();
//
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}

    	

    }

}
