package br.com.etico.arquitetura.formulario.util;

import java.util.Set;

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionActionListener;

/**
 * 
 * @author Thiago Luiz Rodrigues
 * 
 */
public class ELutil {

	/**
	 * Mata uma sess�o aberta
	 * 
	 * @param beanName
	 */
	public static void killSessionBean(String beanName) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
		} catch (FacesException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Mata tudo que tiver em sess�o aberto
	 * 
	 */
	public static void limparTodaSessao() {
		
		Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().keySet();
		
		for (String key : keys ) {
			 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
		}
		
	}
	
	/**
	 * Cria um m�thodo para o atributo action da taglib
	 * 
	 * @param valueExpression
	 * @param valueType
	 * @param expectedParamTypes
	 * @return
	 */
	public static MethodExpression createMethodExpression(
			String valueExpression, Class<?> valueType,
			Class<?>[] expectedParamTypes) {

		MethodExpression methodExpression = null;
		try {
			ExpressionFactory factory = FacesContext.getCurrentInstance()
					.getApplication().getExpressionFactory();
			methodExpression = factory.createMethodExpression(FacesContext
					.getCurrentInstance().getELContext(), valueExpression,
					valueType, expectedParamTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodExpression;
	}

	/**
	 * Cria um m�thodo para o atributo actionListener da taglib
	 * 
	 * @param valueExpression
	 * @param valueType
	 * @param expectedParamTypes
	 * @return
	 */
	public static MethodExpressionActionListener createMethodActionListener(
			String valueExpression, Class<?> valueType,
			Class<?>[] expectedParamTypes) {

		MethodExpressionActionListener actionListener = null;
		try {
			actionListener = new MethodExpressionActionListener(
					createMethodExpression(valueExpression, valueType,
							expectedParamTypes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionListener;
	}

}
