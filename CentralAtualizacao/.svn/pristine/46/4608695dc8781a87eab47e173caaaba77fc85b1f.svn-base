package br.com.etico.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;

import br.com.etico.arquitetura.exception.RestricaoException;
import br.com.etico.arquitetura.formulario.Formulario;
import br.com.etico.arquitetura.formulario.LOV;
import br.com.etico.arquitetura.formulario.TemLOV;
import br.com.etico.modelo.beans.teste.ClienteTeste;
import br.com.etico.modelo.beans.teste.RegistroTeste;
import br.com.etico.persistencia.dao.GenericDAO;
import br.com.etico.persistencia.dao.teste.ClienteTesteDAO;
import br.com.etico.persistencia.dao.teste.RegistroTesteDAO;


@Named
@SessionScoped
public class ExemploForm extends Formulario<RegistroTeste> implements TemLOV, Serializable {
	
	@Inject
	private RegistroTesteDAO registroTesteDAO;
	
	@Inject
	private ClienteTesteDAO clienteTesteDAO;
	
	private final LOV<ClienteTeste> LovClienteTeste  = new LOV<>(this, ClienteTeste.class);
	
	@PostConstruct
	public void inicializaDAO() {
		setDaoPrincipal(getRegistroTesteDAO());
		
		getLovClienteTeste().setDaoPrincipal(getClienteTesteDAO());
	}

	@Override
	public boolean checaRestricoes() throws Exception {
		
		if (getEntidade().getNome() == null || getEntidade().getNome().trim().isEmpty()) {
			return getRestricaoMensagem("Nome invalido");
		}
		
		if (getEntidade().getClienteTeste() == null) {
			return getRestricaoMensagem("Selecione o cliente");
		}
		
		return true;
		
	}
	
	//**********************************************//
	//												//
	//                     LOVS                     //
	//												//
	//**********************************************//
	
	@Override
	public Criteria filtroNaLov(LOV<?> lov, GenericDAO<?> daoPrincipal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preencheCampoLov(Object object, LOV<?> lov) {
		
		if (lov == getLovClienteTeste()) {
			
			getEntidade().setClienteTeste((ClienteTeste)object);
			
		}
		
	}

	@Override
	public void removeCampoLov(LOV<?> lov) {
		
		if (lov == getLovClienteTeste()) {
			
			getEntidade().setClienteTeste(null);
			
		}
		
	}
	
	//********************** FIM LOV ********************//
	
	
	@Override
	public void limparTela() {
		setEntidade(new RegistroTeste());
	}

	public RegistroTesteDAO getRegistroTesteDAO() {
		return registroTesteDAO;
	}

	public void setRegistroTesteDAO(RegistroTesteDAO registroTesteDAO) {
		this.registroTesteDAO = registroTesteDAO;
	}

	public LOV<ClienteTeste> getLovClienteTeste() {
		return LovClienteTeste;
	}

	public ClienteTesteDAO getClienteTesteDAO() {
		return clienteTesteDAO;
	}

	public void setClienteTesteDAO(ClienteTesteDAO clienteTesteDAO) {
		this.clienteTesteDAO = clienteTesteDAO;
	}

}
