package br.com.etico.ir.tokenizacao;

import java.text.Normalizer;
import java.util.List;

import br.com.etico.ir.utils.Termo;
import br.com.etico.ir.utils.unicoTermoArrayListIR;

public class Tokenizacao  {

	// LISTA DE STOPWORDS
	public static void main(String[] args) {
		
//		System.out.println(Math.log(a));
		
	}
	
	
	
	/**
	 * 
	 * Tem a função de retirar acentos e caracter especiais dos textos.
	 * 
	 * 
	 * @param texto
	 * @return
	 */
	public String textNormalizer(String texto) {
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		return texto.replaceAll("[^\\p{ASCII}]", "");
	}
	
	private boolean isNumero(String ch) {
		
		try {
			new Long(ch);
		} catch (Exception ex) {
			return false;
		}
		
		return true;
		
	}

	/**
	 * 
	 * Tem a função de transformar as palavras em letras minusculas remover
	 * pontos, vergulas e acentos;
	 * 
	 * @param stringBuilder
	 * @return
	 */
	public List<Termo> tokenizadorTermo(StringBuilder stringBuilder) {

		String[] texto = stringBuilder.toString().split(" "); // ".split(" ");

		List<Termo> lisTokens = new unicoTermoArrayListIR<>();

		for (String palavra : texto) {

			String[] letras = palavra.trim().split("");

			String token = "";

			for (String letra : letras) {

				// transforma a letra em minusculo
				letra = letra.toLowerCase();

				if (letra.equals(",") || letra.equals(".") || letra.equals("/")
						|| letra.equals("\\") || letra.equals("-")
						|| letra.equals("*")
						|| letra.equals(":")
						|| letra.equals("]")
						|| letra.equals("[")
						|| letra.equals("{")
						|| letra.equals("}")
						|| letra.equals("=")
						|| isNumero(letra)) {
					continue;
				}

				token += letra;
			}

			//
			// VERIFICA SE NÃO É UMA STOPWORDS
			//
			token = textNormalizer(token);

			if (!StopWords.isStopWord(token)) {
				lisTokens.add(new Termo(token));
			}
			

		}

		/**
		 * 
		 * Calcula a frequencia do termo no documento TF
		 * 
		 */
		
		for (Termo termo : lisTokens) {
			
			double tf = (Double.valueOf(termo.getQtdTermo()) / ((unicoTermoArrayListIR<Termo>)lisTokens).getSizeGeral());
			termo.setFrequenciaDoTermoTF(tf);
			
		}

		return lisTokens;

	}
	

	
	//	public double tf(List<Termo> termos, String term) {
//	    double result = 0;
//	    for (Termo termo : termos) {
//	       if (term.equalsIgnoreCase(termo.getTermo()))
//	              result++;
//	       }
//	    return result / termos.size();
//	}

}
