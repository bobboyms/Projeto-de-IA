package br.com.etico.arquitetura.formulario;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.event.SelectEvent;

import br.com.etico.persistencia.EntidadePersistence;
import br.com.etico.persistencia.dao.GenericDAO;


public class LOV<T> {
	
//	private DataModel<T> dataModel;
	private LOV<T> lov;
	private TemLOV formPrincipal;
	private Class<T> classePadrao;
	private GenericDAO<T> daoPrincipal;
	
	private Long idSelecionado;
	
	@SuppressWarnings("unused")
	private List<T> registros;
	
	public LOV(TemLOV formPrincipal, Class<T> classePadrao) {
		setLov(this);
		setFormPrincipal(formPrincipal);
		setClassePadrao(classePadrao);
//		setDaoPrincipal(new GenericDAO<T>(getClassePadrao()));
//		setDataModel(new DataModel<T>(getDaoPrincipal()));
	}
	
//	public LOV(Class<T> classePadrao) {
//		setLov(this);
//		setClassePadrao(classePadrao);
//		setDaoPrincipal(new GenericDAO<T>(getClassePadrao()));
//		setDataModel(new DataModel<T>(getDaoPrincipal()));
//	}
	
	public final void onChange(final AjaxBehaviorEvent event) {
		
		if (getIdSelecionado() == null || getIdSelecionado().equals(0l)) {
			return;
		}
		
		Object object = getDaoPrincipal().getRegistro(idSelecionado);
		
		if (object == null) return;
		
		getFormPrincipal().preencheCampoLov(object, this);
		
	    
	}
	
	
	
	public final void selecionar(SelectEvent ev) {
		
		try {
			
			setIdSelecionado(((EntidadePersistence)ev.getObject()).getId());
			
			getFormPrincipal().preencheCampoLov(ev.getObject(), this);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public final void remover(ActionEvent ev) {
		
		try {
			
			setIdSelecionado(null);
			
			getFormPrincipal().removeCampoLov(this);
			
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
		
		ct.addOrder(Order.asc(EntidadePersistence.strId));
		
		return ct.list();
		
	}


	public void setRegistros(List<T> registros) {
		this.registros = registros;
	}

	public TemLOV getFormPrincipal() {
		return formPrincipal;
	}

	public void setFormPrincipal(TemLOV formPrincipal) {
		this.formPrincipal = formPrincipal;
	}

	public Class<T> getClassePadrao() {
		return classePadrao;
	}

	public void setClassePadrao(Class<T> classePadrao) {
		this.classePadrao = classePadrao;
	}

	public LOV<T> getLov() {
		return lov;
	}

	public void setLov(LOV<T> lov) {
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

	
}
