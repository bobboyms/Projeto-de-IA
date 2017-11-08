package br.com.etico.utils;

import org.junit.Assert;
import org.junit.Test;

public class TestesUnitarios {

	
	public static boolean validaSenha(String senha) {
		
		System.out.println("SENHA : " + senha);
		
		long somenteNumero = 0l;
		
		try {
			somenteNumero = new Long(senha);
			return false;
			
		} catch (Exception e) {}
		
		
		if (senha.trim().isEmpty()) {
			return false;
		} else if (senha.trim().length() < 6) {
			return false;
		}
		
		
		return true;
		
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void TesteDeSenhaForte() {
		System.out.println(validaSenha("123$a"));
		System.out.println(validaSenha("ax$123"));
		System.out.println(validaSenha("123665221144422554"));
		System.out.println(validaSenha("xSdR98"));
	}
	
}
