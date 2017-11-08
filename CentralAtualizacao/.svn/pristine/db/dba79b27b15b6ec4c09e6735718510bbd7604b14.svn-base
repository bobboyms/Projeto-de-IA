package br.com.etico.arquitetura.formulario;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;

import br.com.etico.persistencia.EntidadePersistence;
import br.com.etico.persistencia.dao.GenericDAO;


public class ListaLOV<T> {
	
	private ListaLOV<T> lov;
	private TemListaLOV formPrincipal;
	private Class<T> classePadrao;
	private GenericDAO<T> daoPrincipal;
	private T entidade;  
	
	private List<T> listaDeRegistros = new ArrayList<>();
	private List<T> listaDeRegistrosRemovidos = new ArrayList<>();
	
	private Long idSelecionado;
	
	@SuppressWarnings("unused")
	private List<T> registros;
	
	public ListaLOV(TemListaLOV formPrincipal, Class<T> classePadrao) {
		setLov(this);
		setFormPrincipal(formPrincipal);
		setClassePadrao(classePadrao);
	}
	
	@SuppressWarnings("unchecked")
	public final void onChange(final AjaxBehaviorEvent event) {
		
		if (getIdSelecionado() == null || getIdSelecionado().equals(0l)) {
			return;
		}
		
		Object object = getDaoPrincipal().getRegistro(idSelecionado);
		
		if (object == null) return;
		
		setEntidade((T) object);
	    
	}
	
	
	
	@SuppressWarnings("unchecked")
	public final void selecionar(SelectEvent ev) {
		
		try {
			
			setIdSelecionado(((EntidadePersistence)ev.getObject()).getId());
			
			setEntidade((T)ev.getObject());
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public final void adicionarNaLista(ActionEvent ev) {
		
		if (getEntidade() == null || ((EntidadePersistence)getEntidade()).getId().equals(0l) ) {
			return;
		}
		
		getListaDeRegistros().add(getEntidade());
		getListaDeRegistrosRemovidos().remove(getEntidade());
		
		setEntidade(null);
		
	}
	
	public final void setRemoverDaLista(T objeto) {
		
		try {
			
			getListaDeRegistros().remove(objeto);
			getListaDeRegistrosRemovidos().add(objeto);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
//	public DataModel<T> getDataModel() {
//		
//		dataModel.setCtLov(getFormPrincipal().filtroNaLov(this,getDaoPrincipal()));
//		
//		return dataModel;
//	}
//
//	public void setDataModel(DataModel<T> dataModel) {
//		this.dataModel = dataModel;
//	}

	@SuppressWarnings("unchecked")
	public List<T> getRegistros() {
		
		if (getDaoPrincipal() == null) {
			try {
				throw new Exception("Dao principal não setada para a LOV : " + this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Criteria ct = getFormPrincipal().filtroNaLov(this, getDaoPrincipal());
		
		if (ct == null) {
			ct = getDaoPrincipal().getCriteria();
			ct.addOrder(Order.asc(EntidadePersistence.strId));
		}
		
		List<Long> idsSelecionados = new ArrayList<>();
		
		for (T registro : getListaDeRegistros()) {
			idsSelecionados.add(((EntidadePersistence)registro).getId());
		}
		
		if (idsSelecionados.size() > 0) {
			Criterion in = Restrictions.in(EntidadePersistence.strId, idsSelecionados);  
			ct.add(Restrictions.not(in));
		}
		
		
		ct.addOrder(Order.asc(EntidadePersistence.strId));
		
		return ct.list();
		
	}


	public void setRegistros(List<T> registros) {
		this.registros = registros;
	}

	public Class<T> getClassePadrao() {
		return classePadrao;
	}

	public void setClassePadrao(Class<T> classePadrao) {
		this.classePadrao = classePadrao;
	}

	public ListaLOV<T> getLov() {
		return lov;
	}

	public void setLov(ListaLOV<T> lov) {
		this.lov = lov;
	}

	public GenericDAO<T> getDaoPrincipal() {
		return daoPrincipal;
	}

	public void setDaoPrincipal(GenericDAO<T> daoPrincipal) {
		this.daoPrincipal = daoPrincipal;
	}

	public Long getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(Long idSelecionado) {
		this.idSelecionado = idSelecionado;
	}

	public TemListaLOV getFormPrincipal() {
		return formPrincipal;
	}

	public void setFormPrincipal(TemListaLOV formPrincipal) {
		this.formPrincipal = formPrincipal;
	}

	public List<T> getListaDeRegistros() {
		return listaDeRegistros;
	}

	public void setListaDeRegistros(List<T> listaDeRegistros) {
		this.listaDeRegistros = listaDeRegistros;
	}

	public T getEntidade() {
		return entidade;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public List<T> getListaDeRegistrosRemovidos() {
		return listaDeRegistrosRemovidos;
	}

	public void setListaDeRegistrosRemovidos(List<T> listaDeRegistrosRemovidos) {
		this.listaDeRegistrosRemovidos = listaDeRegistrosRemovidos;
	}

	
}
