package br.com.email;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ClienteForm implements Serializable {

	public String getNome() {
		return "juse";
	}
	
}
