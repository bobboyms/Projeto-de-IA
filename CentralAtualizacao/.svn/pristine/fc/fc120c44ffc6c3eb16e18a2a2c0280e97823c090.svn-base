package br.com.etico.arquitetura.exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class RestricaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public RestricaoException(String menssagem) {
		super(menssagem);
		if (FacesContext.getCurrentInstance() != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, menssagem, ""));
		}
	}
	
}
