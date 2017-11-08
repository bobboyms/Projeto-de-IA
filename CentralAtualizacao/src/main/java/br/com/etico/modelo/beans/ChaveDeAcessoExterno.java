package br.com.etico.modelo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.etico.persistencia.EntidadePersistence;

@Entity
@Table(name = "tb_chave_acesso_externo")
public class ChaveDeAcessoExterno extends EntidadePersistence {
	
	private static final long serialVersionUID = 1L;

	public static final String strChave = "chave";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="chave", nullable=false)
	private String chave;
	

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
		
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}
	
}
