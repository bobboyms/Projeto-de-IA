package br.com.etico.modelo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_perfil")
public class Perfil extends EntidadePersistence {
	
	private static final long serialVersionUID = 1L;

	public static final String strIndTipoSetor = "indTipoSetor";
	
	public static final String strIndTipoSetor_SUPORTE = "SU";
	public static final String strIndTipoSetor_TI = "TI";
	public static final String strIndTipoSetor_CLIENTE = "CL";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome")
	private String nome;
	
	/**
	 * 
	 *  SU = SUPORTE
	 *  TI = T.I
	 * 
	 */
	
	@Column(name="ind_tipo_setor", nullable=false)
	private String indTipoSetor;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setValoresPadroes() {
		// TODO Auto-generated method stub
	}

	public String getIndTipoSetor() {
		return indTipoSetor;
	}
	
	
	@Transient
	public String getIndTipoSetorFormatado() {
		
		/**
		 * 
		 *  SU = SUPORTE
		 *  TI = T.I
		 * 
		 */
		
		if (getIndTipoSetor() == null) return null;
		if (getIndTipoSetor().equals(Perfil.strIndTipoSetor_SUPORTE)) return "SUPORTE";
		if (getIndTipoSetor().equals(Perfil.strIndTipoSetor_TI)) return "T.I";
		if (getIndTipoSetor().equals(Perfil.strIndTipoSetor_CLIENTE)) return "CLIENTE";
		
		return null;
		
	}

	public void setIndTipoSetor(String indTipoSetor) {
		this.indTipoSetor = indTipoSetor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
