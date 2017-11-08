package br.com.etico.modelo.beans.teste;

import java.io.Serializable;

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
@Table(name = "tb_registro_teste")
public class RegistroTeste extends EntidadePersistence implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String strNome = "nome";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente_teste")
	private ClienteTeste clienteTeste;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setValoresPadroes() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ClienteTeste getClienteTeste() {
		return clienteTeste;
	}

	public void setClienteTeste(ClienteTeste clienteTeste) {
		this.clienteTeste = clienteTeste;
	}

}
