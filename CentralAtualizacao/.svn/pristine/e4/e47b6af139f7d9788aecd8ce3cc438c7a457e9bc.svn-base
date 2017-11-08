package br.com.etico.arquitetura.formulario;

import org.hibernate.Criteria;

import br.com.etico.persistencia.dao.GenericDAO;


/**
 * 
 * @author Thiago Luiz Rodrigues
 *
 */
public interface TemListaLOV {
	
	public Criteria filtroNaLov(ListaLOV<?> listaLOV, GenericDAO<?> daoPrincipal);	
	
	public void preencheCampoLov(Object object, ListaLOV<?> lov);
	
	public void removeCampoLov(ListaLOV<?> lov, Object object);
	
}
