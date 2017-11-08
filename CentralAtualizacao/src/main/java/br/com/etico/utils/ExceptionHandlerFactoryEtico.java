package br.com.etico.utils;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ExceptionHandlerFactoryEtico extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	// this injection handles jsf
	public ExceptionHandlerFactoryEtico(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	// create your own ExceptionHandler
	@Override
	public ExceptionHandler getExceptionHandler() {
		return new ExceptionHandlerEtico(this.parent.getExceptionHandler());
	}

}
